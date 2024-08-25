package com.projectmedicine.gateway.gateway.GatewayComponent.Controllers;

import com.projectmedicine.gateway.gateway.AuthenticationClient.protoComponents.Authentication;
import com.projectmedicine.gateway.gateway.GatewayComponent.Clients.no_sql.ConsultationsClient;
import com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql.AppointmentsClient;
import com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql.AuthenticationClient;
import com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql.DoctorsClient;
import com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql.PatientsClient;
import com.projectmedicine.gateway.gateway.Utils.Exceptions.ConflictException;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.*;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.responses.AuthenticationResponse;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.responses.ConsultationsResponse;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.*;

import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.*;


@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
@RestController
@RequestMapping("/api/medical_office/gateway")
public class GatewayController {
    @Autowired
    private AppointmentsClient appointmentsClient;
    @Autowired
    private ConsultationsClient consultationsClient;
    @Autowired
    private PatientsClient patientsClient;
    @Autowired
    private DoctorsClient doctorsClient;
    @Autowired
    private AuthenticationClient authenticationClient;


    @PreAuthorize("hasRole('PATIENT')") //DUMMY FOR TEST SECURITY CONFIG -> TO BE IGNORED
    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages(){
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }
    /*--------------------------------------------------- PATIENTS -------------------------------------------------------------------------*/
    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatients(
            @RequestParam(value = "lastname") Optional<String> lastname,
            @RequestParam(value = "firstname") Optional<String> firstname,
            @RequestParam(value = "email") Optional<String> email,
            @RequestParam(value = "phone") Optional<String> phone,
            @RequestParam(value = "birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> birthdate
    ) {
            ResponseEntity<?> response = patientsClient.getAllPatients(lastname, firstname, email, phone, birthdate);
            log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patients/user/{idUser}")
    public ResponseEntity<?> getPatientByIdUser(@PathVariable Integer idUser) {
            log.info("getpatient gatewway cntrolller");
            return patientsClient.getPatientByIdUser(idUser);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patients/patient/{cnp}")
    public ResponseEntity<?> getPatientByCnp(@PathVariable String cnp) {
        log.info("getpatient gatewway cntrolller");
        return patientsClient.getPatientByCnp(cnp);
    }

    @PostMapping("/patients/register/{cnp}")
    public ResponseEntity<?> registerPatient(
            @PathVariable final String cnp,
            @RequestBody CreatePatientRequest bodyPatientIDM
    ) {
        log.info("createPatient gatewway cntrolller");
        /* First, call register method to create user in IDM project */
        ResponseEntity<?> newUser = authenticationClient.register(
                new RegisterRequest(
                        bodyPatientIDM.getEmail(),
                        bodyPatientIDM.getPassword(),
                        bodyPatientIDM.getRole()
                )
        );
        log.info("Newuser {} ", newUser);
        if( HttpStatus.CREATED != newUser.getStatusCode())
        {
            /* If is not success case, just return already created response entity */
            return newUser;
        }
        AuthenticationResponse authenticationResponse =   AuthenticationResponse.builder()
                .accessToken((String) newUser.getBody())
                .build();
        log.info("INTREGISTRARE CU SUCCES. PRIMESC TOKEN  " + newUser.getBody());
        log.info("IL PUN IN AUTHRESPONSE " + authenticationResponse);

        /* Then get real user_id from IDM project */
        ResponseEntity<?> userIDM = authenticationClient.getUserByEmail(bodyPatientIDM.getEmail());
        if( HttpStatus.OK != userIDM.getStatusCode())
        {
            /* If is not success case, just return already created response entity */
            return userIDM;
        }
        log.info("useridm {}", userIDM);
        Authentication.UserBodyProto userBodyProto = (Authentication.UserBodyProto) userIDM.getBody();

        Integer userIdIDM = ((Authentication.UserBodyProto) userIDM.getBody()).getUserId();
        log.info("user id idm {} ", userIdIDM);

        PatientRequest patientRequest =   new PatientRequest(
                bodyPatientIDM.getCnp(),
                userIdIDM,
                bodyPatientIDM.getLastname(),
                bodyPatientIDM.getFirstname(),
                bodyPatientIDM.getEmail(),
                bodyPatientIDM.getPhone(),
                bodyPatientIDM.getBirthdate(),
                bodyPatientIDM.getIsactive()
        );
        log.info("Am trecut de useridm -> mersg spre pacient cu request {}", patientRequest);
        ResponseEntity<?> responseEntity = patientsClient.createOrUpdatePatient(cnp, patientRequest);
        if( HttpStatus.CREATED != responseEntity.getStatusCode() )
        {
            return responseEntity;
        }

        /* In case of both registration in IDM db and create in PATIENTS db -> return token */
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);


    }

    @PostMapping("/patients/authenticate")
    public ResponseEntity<?> authenticatePatient( @RequestBody AuthenticationRequest authenticationRequest)
    {
        /* To autjenticate a client, it must be validate existence of user. For that, I will check first if patient account was deactivated */
        ResponseEntity<?> response = patientsClient.getAllPatients(Optional.empty(), Optional.empty(), Optional.ofNullable(authenticationRequest.getEmail()), Optional.empty(), Optional.empty());
        log.info("authenticatePatient() RESPONSE DIN PROJECTMEDICINE {}", response);
        if(HttpStatus.OK != response.getStatusCode()){
            return new ResponseEntity<>(new ConflictException().getMessage(), HttpStatus.CONFLICT);

        }

        CollectionModel<EntityModel<?>> collectionModel = (CollectionModel<EntityModel<?>>) response.getBody();

        if (collectionModel != null && collectionModel.getContent().size() == 1){
            LinkedHashMap<String, Object> patientData = (LinkedHashMap<String, Object>) collectionModel.getContent().iterator().next().getContent();

            Boolean isPatientActive = (Boolean) patientData.get("isactive");

            if (!isPatientActive) {
                return new ResponseEntity<>(new ConflictException(CONFLICT_EXCEPTION_USER_BLOCKED).getMessage(), HttpStatus.CONFLICT);
            }
        }

//        return authenticationClient.authenticate(authenticationRequest);
        ResponseEntity<?> responseEntity = authenticationClient.authenticate(authenticationRequest);
        log.info("Newuser {} ", responseEntity);
        if( HttpStatus.OK != responseEntity.getStatusCode())
        {
            /* If is not success case, just return already created response entity */
            return responseEntity;
        }
        AuthenticationResponse authenticationResponse =   AuthenticationResponse.builder()
                .accessToken((String) responseEntity.getBody())
                .build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/patients/edit/{cnp}") /* Seems not ok path, but..*/ // TODO PUT SAU POST?
    public ResponseEntity<?> editPatient(
            @PathVariable final String cnp,
            @RequestBody PatientRequest patientRequest
    ){
        log.info("EDIT ");
        /* Endpoint frok Patient Project can create/update an user, but for set properly id_user from idm, it is necessary to
        * control and limit this logic in gateway peoject. In this way,
        *  a patient is not created with invalid id_user. Also, is updated just if exists. */
        log.info("try to get patient by email ");
        ResponseEntity<?> response = patientsClient.getAllPatients(Optional.empty(), Optional.empty(), Optional.ofNullable(patientRequest.getEmail()), Optional.empty(), Optional.empty());
        if(HttpStatus.OK != response.getStatusCode()){
            log.info("status code not ok {}", response.getStatusCode());
            return response;
        }

        log.info("status code ok -> identify user in db ");
        CollectionModel<EntityModel<?>> collectionModel = (CollectionModel<EntityModel<?>>) response.getBody();
        log.info("COREECT FIELDS , BUT NO RESOURCE FOUND {}",collectionModel.getContent().size() );
        if (collectionModel != null && collectionModel.getContent().size() == 1) {
            LinkedHashMap<String, Object> patientData = (LinkedHashMap<String, Object>) collectionModel.getContent().iterator().next().getContent();
            log.info("here is user {} ",patientData );
            if( patientData.get("email").equals(patientRequest.getEmail())){
                log.info("patient already exist so edit");
                /* Is a temorary logic for edit */
                patientRequest.setIdUser((Integer) patientData.get("idUser"));
                log.info("TRIMIT SPRE EDITARE PACIENTUL {}", patientRequest);
                /* just to be sure that if statement is exceuted with correct resource*/
                return patientsClient.createOrUpdatePatient(cnp, patientRequest);
            }
        }
        log.info("patient seems not exist");
        return new ResponseEntity<>(new ConflictException().getMessage(), HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping("/patients/{cnp}")
    public ResponseEntity<?> deletePatient(@PathVariable String cnp) {
            return patientsClient.deletePatient(cnp);
    }


    /*--------------------------------------------------- DOCTORS -------------------------------------------------------------------------*/
    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('ADMIN')")
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value="itemsPerPage", defaultValue = "3" ) Optional<Integer> itemsPerPage,
            @RequestParam(value = "lastname") Optional<String> lastname,
            @RequestParam(value = "firstname") Optional<String> firstname,
            @RequestParam(value = "email") Optional<String> email,
            @RequestParam(value = "phone") Optional<String> phone,
            @RequestParam(value = "specialization") Optional<String> specialization
    ) {
        ResponseEntity<?> response = doctorsClient.getAllDoctors(page, itemsPerPage, lastname, firstname, email, phone, specialization);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    @GetMapping("/doctors/user/{idUser}")
    public ResponseEntity<?> getDoctorByIdUser(@PathVariable Integer idUser) {
        log.info("getpatient gatewway cntrolller");
        return doctorsClient.getDoctorByIdUser(idUser);
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('ADMIN')")
    @GetMapping("/doctors/doctor/{idDoctor}")
    public ResponseEntity<?> getDoctorByCnp(@PathVariable Integer idDoctor) {
        log.info("getpatient gatewway cntrolller");
        return doctorsClient.getDoctorByIdDoctor(idDoctor);
    }

    @PostMapping ("/doctors/register")
    public ResponseEntity<?> registerDoctor(
            @RequestBody CreateDoctorRequest bodyDoctorIDM
    ) {
        log.info("createDoctor gatewway cntrolller");
        /* First, call register method to create user in IDM project */
        ResponseEntity<?> newUser = authenticationClient.register(
                new RegisterRequest(
                        bodyDoctorIDM.getEmail(),
                        bodyDoctorIDM.getPassword(),
                        bodyDoctorIDM.getRole()
                )
        );
        log.info("Newuser {} ", newUser);
        if( HttpStatus.CREATED != newUser.getStatusCode())
        {
            /* If is not success case, just return already created response entity */
            return newUser;
        }
        AuthenticationResponse authenticationResponse =   AuthenticationResponse.builder()
                .accessToken((String) newUser.getBody())
                .build();
        log.info("INTREGISTRARE CU SUCCES DOCTOR. PRIMESC TOKEN  " + newUser.getBody());
        log.info("IL PUN IN AUTHRESPONSE " + authenticationResponse);
        /* Then get real user_id from IDM project */
        ResponseEntity<?> userIDM = authenticationClient.getUserByEmail(bodyDoctorIDM.getEmail());
        if( HttpStatus.OK != userIDM.getStatusCode())
        {
            /* If is not success case, just return already created response entity */
            return userIDM;
        }
        log.info("useridm {}", userIDM);
        Authentication.UserBodyProto userBodyProto = (Authentication.UserBodyProto) userIDM.getBody();

        Integer userIdIDM = ((Authentication.UserBodyProto) userIDM.getBody()).getUserId();
        log.info("user id idm {} ", userIdIDM);



        DoctorRequest doctorRequest =   new DoctorRequest(
                null,
                userIdIDM,
                bodyDoctorIDM.getLastname(),
                bodyDoctorIDM.getFirstname(),
                bodyDoctorIDM.getEmail(),
                bodyDoctorIDM.getPhone(),
                bodyDoctorIDM.getSpecialization()
        );
        log.info("Am trecut de useridm -> mersg spre doctor cu request {}", doctorRequest);
       // return doctorsClient.createDoctor(doctorRequest);
        ResponseEntity<?> responseEntity = doctorsClient.createDoctor(doctorRequest);
        if( HttpStatus.CREATED != responseEntity.getStatusCode() )
        {
            return responseEntity;
        }

        /* In case of both registration in IDM db and create in PATIENTS db -> return token */
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }



    @PostMapping("/doctors/authenticate")
    public ResponseEntity<?> authenticateDoctor( @RequestBody AuthenticationRequest authenticationRequest)
    {
        /* To autjenticate a client, it must be validate existence of user. For that, I will check first if doctor account was deactivated */
        ResponseEntity<?> response = doctorsClient.getAllDoctors(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.ofNullable(authenticationRequest.getEmail()), Optional.empty(), Optional.empty());
        if(HttpStatus.OK != response.getStatusCode()){
            return response;
        }

        CollectionModel<EntityModel<?>> collectionModel = (CollectionModel<EntityModel<?>>) response.getBody();

        if (collectionModel != null && collectionModel.getContent().size() == 1) {
            LinkedHashMap<String, Object> doctorData = (LinkedHashMap<String, Object>) collectionModel.getContent().iterator().next().getContent();

            if(authenticationRequest.getEmail().equals(doctorData.get("email")))
            {
                ResponseEntity<?> responseEntity = authenticationClient.authenticate(authenticationRequest);
                log.info("Newuser {} ", responseEntity);
                if( HttpStatus.OK != responseEntity.getStatusCode())
                {
                    /* If is not success case, just return already created response entity */
                    return responseEntity;
                }
                AuthenticationResponse authenticationResponse =   AuthenticationResponse.builder()
                        .accessToken((String) responseEntity.getBody())
                        .build();
                return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
            }

        }
        //todo aici returnez conflic pentru ca fac cerere pe resursa inexistenta la logare sau clasic 404 ?
        return new ResponseEntity<>(new ConflictException().getMessage(), HttpStatus.CONFLICT);

    }


    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/doctors/edit/{idDoctor}") // TODO PUT SAU POST?
    public ResponseEntity<?> editDoctor(
            @PathVariable final Integer idDoctor,
            @RequestBody DoctorRequest doctorRequest
    ){
        log.info("EDIT ");
        /* Endpoint frok Patient Project can create/update an user, but for set properly id_user from idm, it is necessary to
         * control and limit this logic in gateway peoject. In this way,
         *  a patient is not created with invalid id_user. Also, is updated just if exists. */
        log.info("try to get doctor by email ");
        ResponseEntity<?> response = doctorsClient.getAllDoctors(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.ofNullable(doctorRequest.getEmail()), Optional.empty(), Optional.empty());
        if(HttpStatus.OK != response.getStatusCode()){
            log.info("status code not ok {}", response.getStatusCode());
            return response;
        }

        log.info("status code ok -> identify user in db ");
        CollectionModel<EntityModel<?>> collectionModel = (CollectionModel<EntityModel<?>>) response.getBody();
        log.info("COREECT FIELDS , BUT NO RESOURCE FOUND {}",collectionModel.getContent().size() );
        if (collectionModel != null && collectionModel.getContent().size() == 1) {
            LinkedHashMap<String, Object> doctorData = (LinkedHashMap<String, Object>) collectionModel.getContent().iterator().next().getContent();
            log.info("here is user {} ",doctorData );
            if( doctorData.get("email").equals(doctorRequest.getEmail())){
                log.info("patient already exist so edit");
                /* Is a temorary logic for edit */
                doctorRequest.setIdUser((Integer) doctorData.get("idUser"));
                doctorRequest.setIdDoctor((Integer) doctorData.get("idDoctor"));
                /* just to be sure that if statement is exceuted with correct resource*/
                return doctorsClient.updateDoctor(idDoctor, doctorRequest);
            }
        }
        log.info("doctor seems not exist");
        return new ResponseEntity<>(new ConflictException().getMessage(), HttpStatus.CONFLICT);
    }


    /*--------------------------------------------------- APPOINTMENTS -------------------------------------------------------------------------*/
    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('ADMIN')")
    @GetMapping("/appointments")
    public ResponseEntity<?> getAllAppointments(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value="itemsPerPage", defaultValue = "3" ) Optional<Integer> itemsPerPage,
            @RequestParam(value = "status") Optional<String> status

    ) {

        ResponseEntity<?> response = appointmentsClient.getAllAppointments(page, itemsPerPage, status);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT')or hasRole('ADMIN')")
    @GetMapping("/appointments/{cnp}/{idDoctor}/{appointmentTime}")
    public ResponseEntity<?> getAppointmentById(
            @PathVariable String cnp,
            @PathVariable Integer idDoctor,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime appointmentTime
    ) {

        ResponseEntity<?> response = appointmentsClient.getAppointmentByOwnId(cnp, idDoctor, appointmentTime);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PreAuthorize("hasRole('PATIENT')or hasRole('ADMIN')")
    @GetMapping("/appointments/patient/{cnp}")
    public ResponseEntity<?> getPatientAppointments(
            @PathVariable String cnp,
            @RequestParam(value = "date") Optional<Integer> date,
            @RequestParam(value = "type") Optional<String> type,
            @RequestParam(value = "status") Optional<String> status
    ) {
        log.info("[{}] -> GET, getPatientAppointments: cnp:{}, date:{},type:{},status:{}",
                this.getClass().getSimpleName(), cnp, date, type, status);
        ResponseEntity<?> response = appointmentsClient.getPatientAppointments(cnp, date, type, status);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    @GetMapping("/appointments/doctor/{idDoctor}")
    public ResponseEntity<?> getDoctorAppointments(
            @PathVariable Integer idDoctor,
            @RequestParam(value = "date") Optional<Integer> date,
            @RequestParam(value = "type") Optional<String> type,
            @RequestParam(value = "status") Optional<String> status
    ) {
        log.info("[{}] -> GET, getDoctorAppointments: idDoctor:{}, date:{},type:{},status:{}",
                this.getClass().getSimpleName(), idDoctor, date, type, status);
        ResponseEntity<?> response = appointmentsClient.getDoctorAppointments(idDoctor, date, type, status);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/appointments/patient/{cnp}/doctor/{idDoctor}")
    public ResponseEntity<?> createORUpdateAppointment(
            @PathVariable final String cnp,
            @PathVariable final Integer idDoctor,
            @RequestBody AppointmentRequest appointment
    ) {
        log.info("[{}] -> PUT, createORUpdateAppointment: cnp:{}, idDoctor:{},  appointment:{}",
                this.getClass().getSimpleName(),cnp, idDoctor, appointment);
        ResponseEntity<?> response = appointmentsClient.createORUpdateAppointment(cnp,idDoctor, appointment);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        //return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        return response;
    }


    /*--------------------------------------------------- CONSULTATIONS -------------------------------------------------------------------------*/

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT')or hasRole('ADMIN')")
    @GetMapping("/consultations")
    public ResponseEntity<?> getAllConsultations(
            @RequestParam(value = "page")   Optional<Integer> page,
            @RequestParam(value="itemsPerPage", defaultValue = "3" )Optional<Integer> itemsPerPage,
            @RequestParam(value = "idPatient") Optional<String> idPatient,
            @RequestParam(value = "idDoctor") Optional<Integer> idDoctor,
            @RequestParam(value = "diagnostic") Optional<String> diagnostic,
            @RequestParam(value = "appointmentTime")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            Optional<LocalDateTime> appointmentTime

    ) {
        log.info("[{}] -> GET, getAllConsultations: page:{}, itemsPerPage:{}, idPatient:{}, idDoctor:{}, diagnostic:{}, appointmentTime:{}",
                this.getClass().getSimpleName(), page, itemsPerPage, idPatient, idDoctor, diagnostic, appointmentTime);
        ResponseEntity<?> response = consultationsClient.getAllConsultations(page, itemsPerPage, idPatient, idDoctor, diagnostic, appointmentTime);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }




    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/consultations/doctor/consultation/{cnp}/{idDoctor}/{appointmentTime}")
    public ResponseEntity<?> createConsultation(
            @PathVariable String cnp,
            @PathVariable Integer idDoctor,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime appointmentTime,
            @RequestBody ConsultationsResponse consultationsResponse
    ) {
        log.info("GATEWAY createConsultation");


        if (!cnp.equals(consultationsResponse.getIdPatient())
                || !idDoctor.equals(consultationsResponse.getIdDoctor())
                || !appointmentTime.equals(consultationsResponse.getAppointmentTime())
        ) {
            return new ResponseEntity<>(CONFLIC_EXCEPTION_NOT_SAME_ID, HttpStatus.CONFLICT);
        }

        log.info("verific existenta programare");
        ResponseEntity<?> appointmentResponseEntity =
                appointmentsClient.getAppointmentByOwnId(cnp, idDoctor, appointmentTime);
        log.info("am preluat raspunsul");
        if (appointmentResponseEntity.getStatusCode() != HttpStatus.OK) {
            log.info("programare nu exista");
            return new ResponseEntity<>("Programarea nu exista pentru consultatia curenta.", HttpStatus.NOT_FOUND);
        }
        log.info("programare exista -> incerc crearea consultatiei ");
        ResponseEntity<?> response = consultationsClient.createConsultation(consultationsResponse);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());

    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping ("/consultations/consultation/{_id}/investigation")
    public ResponseEntity<?> createInvestigation(
            @PathVariable final String _id,
            @RequestBody InvestigationRequest investigationRequest
    ) {
        log.info("[{}] -> GET, createInvestigation: _id:{}, investigationRequest:{}", _id, investigationRequest);
        ResponseEntity<?> response = consultationsClient.createInvestigation(investigationRequest);
        log.info("responseEntity.getBody(){}  responseEntity.getStatusCode(){}", response.getBody(), response.getStatusCode());

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }


    /*--------------------------------------------------- AUTHENTICATE GRPC -------------------------------------------------------------------------*/
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        log.info("A client is logging out...");

        return authenticationClient.logout(authorizationHeader);

    }

}
