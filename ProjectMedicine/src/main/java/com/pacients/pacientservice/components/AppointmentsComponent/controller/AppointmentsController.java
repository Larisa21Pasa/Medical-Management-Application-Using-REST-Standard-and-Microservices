package com.pacients.pacientservice.components.AppointmentsComponent.controller;

import com.pacients.pacientservice.components.AppointmentsComponent.hateoas.SelfParent.AppointmentsHateoasSelfParent;
import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.AppointmentsComponent.model.repository.AppointmentsRepository;
import com.pacients.pacientservice.components.AppointmentsComponent.service.AppointmentsServiceImplementation;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.DoctorsComponent.service.DoctorsServiceImplementation;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import com.pacients.pacientservice.components.PatientsComponent.service.PatientsServiceImplementation;
import com.pacients.pacientservice.utils.Exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.CONFLIC_EXCEPTION_NOT_SAME_ID;
import static com.pacients.pacientservice.utils.Logs.ProgramLogs.NOT_FOUND_EXCEPTION;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/api/medical_office/appointments")
public class AppointmentsController {
    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private AppointmentsServiceImplementation appointmentsServiceImplementation;
    @Autowired
    private PatientsServiceImplementation patientsServiceImplementation;
    @Autowired
    private DoctorsServiceImplementation doctorsServiceImplementation;
    /*--------- GET ----------*/
   /*
       NAME METHOD: getAllAppointments from class AppointmentsController
       SCOPE: Retrieve appointment information with optional filters and return a ResponseEntity.

       HTTP CODES:
         *** 200 -> OK
             - Successful retrieval of appointments information returns a 200 response.

         *** 400 -> Bad Request
             - Returned if there is a bad request, such as invalid parameters - > just to catch something that I missed.

         *** 422 -> Unprocessable Entity
             - Returned if there are issues with processing the request.

        *** 406 -> Not acceptable Entity
             - Returned if there are issues with element from body.

         *** 404 -> Not Found
             - Returned if no appointments are found based on the provided filters -> not in current case, but for eventually modification of this function.

       NOTE:
           Retrieve doctor information with optional filters using the HTTP GET method.
           If any optional filters are provided, apply them using the respective service methods and intersect the results.
           Return a ResponseEntity with the appropriate HTTP status code and result.
    */
    @Operation(summary = "Get all/searched appointments resources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - Found all/searched resources",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AppointmentsTable.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid syntax for query params",
                    content = @Content),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Requested representation not acceptable",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid values for query params",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found !",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping()
    public ResponseEntity<?> getAllAppointments(
            @RequestParam(value = "page") @Min(0) Optional<Integer> page,
            @RequestParam(value = "itemsPerPage", defaultValue = "3") @Min(value = 1, message = "Page should be at least 1.") Optional<Integer> itemsPerPage,
            @RequestParam(value = "status") Optional<String> status
    ) {
        log.info("[{}] -> GET, getAllAppointments: appointmentTime:{}, status:{}",
                this.getClass().getSimpleName(), status);
        try {
            CollectionModel<EntityModel<AppointmentsTable>> result = appointmentsServiceImplementation.getAllAppointments();

            if (page.isPresent()) {
                if (page.get() < 0) {
                    appointmentsServiceImplementation.provideHeateoasForPageable(itemsPerPage.orElse(3));
                }
                result = appointmentsServiceImplementation.getAppointmentsByNumberPage(page.get(), itemsPerPage.orElse(3));

            }

            if (status.isPresent()) {
                log.info("status.isPresent()");
                 result = appointmentsServiceImplementation.getAppointmentsByStatus(status.get());

            }
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
        catch (BadRequestException badRequestException) { return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotFoundException notFoundException) { return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND); }
    }


    /*
      NAME METHOD: getAppointmentById from class AppointmentsController
      SCOPE: Retrieve appointment information by own ID and return a ResponseEntity.
      PATH: "/{cnp}/{idDoctor}/{appointmentTime}"
      HTTP CODES:
        *** 200 -> OK
            - Successful retrieval of appointment information returns a 200 response.

        *** 404 -> Not Found
            - Returned if no appointment is found for the provided  ID.
   */
    @Operation(summary = "Get appointment resource by multiple id fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentsTable.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/{cnp}/{idDoctor}/{appointmentTime}")
    public ResponseEntity<?> getAppointmentById(
            @PathVariable String cnp,
            @PathVariable Integer idDoctor,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime appointmentTime
    ) {
        log.info("[{}] -> GET, getAppointmentById: cnp:{}, idDoctor:{}, appointmentTime:{}",
                this.getClass().getSimpleName(), cnp, idDoctor, appointmentTime);
        Optional<AppointmentsTable> appointment = Optional.ofNullable(appointmentsRepository.findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(cnp, idDoctor, appointmentTime));
        log.info("appointments gasit {}", appointment);
        if (appointment.isPresent()) {
            return new ResponseEntity<>(new AppointmentsHateoasSelfParent().toModel(appointment.get()), HttpStatus.OK);
        } else {
            Map<String, ArrayList<Link>> links = new HashMap<>();
            ArrayList<Link> arrayLinks = new ArrayList<Link>();
            Link parentLink = linkTo(methodOn(AppointmentsController.class)
                    .getAllAppointments(Optional.empty(),Optional.empty(),Optional.empty()
                    )
            ).withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<Link>(arrayLinks));

            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }
    }

    /*
     * NAME METHOD: getPatientAppointments from class AppointmentsServiceImplementation
     * SCOPE: Retrieve all or searched appointments for a specific patient with optional filters for date, type, and status.
     *
     * HTTP CODES:
     *   - 200 -> OK
     *           Successful retrieval of all or searched appointments for a specific patient.
     *           Returns a CollectionModel of EntityModel with HATEOAS links.
     *
     *   - 400 -> Bad Request
     *           Invalid syntax for query params triggers a 400 response.
     *
     *   - 422 -> Unprocessable Entity
     *           Invalid values for query params result in a 422 response.
     *
     *   - 404 -> Not Found
     *           List of appointments not found for the specific patient triggers a 404 response.
     */
    @Operation(summary = "Get all/searched appointments for specific patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - Found all/searched resources",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AppointmentsTable.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid syntax for query params",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid values for query params",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found !",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/patient/{cnp}")
    public ResponseEntity<?> getPatientAppointments(
            @PathVariable String cnp,
            @RequestParam(value = "date") Optional<Integer> date,
            @RequestParam(value = "type") Optional<String> type,
            @RequestParam(value = "status") Optional<String> status
    ) {
        log.info("[{}] -> GET, getPatientAppointments: cnp:{}, date:{},type:{},status:{}",
                this.getClass().getSimpleName(), cnp, date, type, status);
        try {
            /* Check if patient Resource actually exist */
            EntityModel<PatientsTable> checkPatient = patientsServiceImplementation.getPatientByCnp(cnp);
            log.info("Patient requiresd {} ",checkPatient);
            if (null == checkPatient) {
                log.info("PATIENT NOT FOUND");
                throw  new NotFoundException(NOT_FOUND_EXCEPTION);
            }
            CollectionModel<EntityModel<AppointmentsTable>> result = appointmentsServiceImplementation.getAppointmentsForSpecificPatient(cnp);

            if (date.isPresent() || (date.isPresent() && type.isPresent())) {
                CollectionModel<EntityModel<AppointmentsTable>> filteredResult = appointmentsServiceImplementation.getAppointmentsForPatientByAppointmentTime(cnp, date.get(), type.get());
                result = appointmentsServiceImplementation.intersectFilteredPatientAppointments(result, filteredResult);
                log.info("date.isPresent() => result {}", result.getContent() );

            }
            if (status.isPresent()) {
                CollectionModel<EntityModel<AppointmentsTable>> filteredResult = appointmentsServiceImplementation.getAppointmentsForPatientByStatus(cnp, status.get());
                result = appointmentsServiceImplementation.intersectFilteredPatientAppointments(result, filteredResult);
                log.info("status.isPresent() => result {}", result.getContent() );

            }

            log.info("NOT result.getContent().isEmpty() {} ", result);
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
        catch (BadRequestException badRequestException) { return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotFoundException notFoundException) { return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND); }
    }



    /*
     * NAME METHOD: getDoctorAppointments from class AppointmentsServiceImplementation
     * SCOPE: Retrieve all or searched appointments for a specific doctor with optional filters for date, type, and status.
     *
     * HTTP CODES:
     *   - 200 -> OK
     *           Successful retrieval of all or searched appointments for a specific doctor.
     *           Returns a CollectionModel of EntityModel with HATEOAS links.
     *
     *   - 400 -> Bad Request
     *           Invalid syntax for query params triggers a 400 response.
     *
     *   - 422 -> Unprocessable Entity
     *           Invalid values for query params result in a 422 response.
     *
     *   - 404 -> Not Found
     *           List of appointments not found for the specific doctor triggers a 404 response.
     */
    @Operation(summary = "Get all/searched appointments for specific doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - Found all/searched resources",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AppointmentsTable.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid syntax for query params",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid values for query params",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found !",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/doctor/{idDoctor}")
    public ResponseEntity<?> getDoctorAppointments(
            @PathVariable Integer idDoctor,
            @RequestParam(value = "date") Optional<Integer> date,
            @RequestParam(value = "type") Optional<String> type,
            @RequestParam(value = "status") Optional<String> status
    ) {
        log.info("[{}] -> GET, getDoctorAppointments: idDoctor:{}, date:{},type:{},status:{}",
                this.getClass().getSimpleName(), idDoctor, date, type, status);
        try {
            /* Check if doctor Resource actually exist */
            EntityModel<DoctorsTable> checkDoctor = doctorsServiceImplementation.getDoctorByIdDoctor(idDoctor);

            if (null == checkDoctor) {
                throw  new NotFoundException(NOT_FOUND_EXCEPTION);
            }
            CollectionModel<EntityModel<AppointmentsTable>> result = appointmentsServiceImplementation.getAppointmentsForSpecificDoctor(idDoctor);

            if (date.isPresent() || (date.isPresent() && type.isPresent())) {

                CollectionModel<EntityModel<AppointmentsTable>> filteredResult = appointmentsServiceImplementation.getAppointmentsForDoctorByAppointmentTime(idDoctor, date.get(), type.get());
                result = appointmentsServiceImplementation.intersectFilteredPatientAppointments(result, filteredResult);
            }
            if (status.isPresent()) {
                CollectionModel<EntityModel<AppointmentsTable>> filteredResult = appointmentsServiceImplementation.getAppointmentsForDoctorByStatus(idDoctor, status.get());
                result = appointmentsServiceImplementation.intersectFilteredPatientAppointments(result, filteredResult);
            }

            if (result.getContent().isEmpty()) {
                return new ResponseEntity<>(appointmentsServiceImplementation.getAllAppointments(), HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);


        }
        catch (BadRequestException badRequestException) {return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);}
        catch (UnprocessableContentException unprocessableContentException) {return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);}
        catch (NotFoundException notFoundException) {return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);}
        catch (RuntimeException runtimeException) {return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND);}
    }

    /*--------- GET ----------*/
    @Operation(summary = "Create/update appointment resource with PUT because ID is manually.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientsTable.class))}),
            @ApiResponse(responseCode = "204", description = "Resource updated successfully",  content = @Content ),
            @ApiResponse(responseCode = "409", description = "Conflict - Resource already exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid content",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Requested representation not acceptable",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),

    })
    @PutMapping("/patient/{cnp}/doctor/{idDoctor}")
    public ResponseEntity<?> createORUpdateAppointment(
            @PathVariable final String cnp,
            @PathVariable final Integer idDoctor,
            @RequestBody AppointmentsTable appointment
    ) {
        log.info("[{}] -> PUT, createORUpdateAppointment: cnp:{}, idDoctor:{},  appointmentTime:{}, status:{}",
                this.getClass().getSimpleName(), appointment.getAppointmentsTablePK().getCnp(), appointment.getAppointmentsTablePK().getIdDoctor(), appointment.getAppointmentsTablePK().getAppointmentTime(), appointment.getStatus());
        try {
            if (null == appointmentsRepository.findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(cnp, idDoctor, appointment.getAppointmentsTablePK().getAppointmentTime())) {
                /* Create Patient resource if NOT find id in database*/
                log.info("Trying to create appointment ... ");
                log.info("CNP {}, {}", cnp, appointment.getAppointmentsTablePK().getCnp());
                log.info("IDDOCTOR {}, {}", idDoctor, appointment.getAppointmentsTablePK().getIdDoctor());
                log.info("!cnp.equals(appointment.getAppointmentsTablePK().getCnp()) {}", !cnp.equals(appointment.getAppointmentsTablePK().getCnp()));
                log.info(" !idDoctor.equals(appointment.getAppointmentsTablePK().getIdDoctor()) {}", !idDoctor.equals(appointment.getAppointmentsTablePK().getIdDoctor()));
                if (!cnp.equals(appointment.getAppointmentsTablePK().getCnp()) || !idDoctor.equals(appointment.getAppointmentsTablePK().getIdDoctor())) {
                    return new ResponseEntity(CONFLIC_EXCEPTION_NOT_SAME_ID, HttpStatus.CONFLICT);
                }

                log.info("Appointment unique, creating ... ");
                EntityModel<AppointmentsTable> newCreatedAppointment = appointmentsServiceImplementation.addAppointment(appointment);    //validarile si verificarile de existenta sunt prezente in serviciu : validare campuri, existenta altui user cu email identic etc
                return new ResponseEntity(newCreatedAppointment, HttpStatus.CREATED);
            }
            else {
                /* Update Patient resource if find id in database*/
                log.info("Trying to update appointment ... ");
                if (!(appointment.getAppointmentsTablePK().getCnp()).equals(cnp) && !(appointment.getAppointmentsTablePK().getIdDoctor()).equals(idDoctor)) {
                    log.info("Path or field cnp/idUser wrong ... ");
                    return new ResponseEntity(CONFLIC_EXCEPTION_NOT_SAME_ID, HttpStatus.CONFLICT);
                }
                log.info("Appointment existing, updating ... ");
                EntityModel<AppointmentsTable> newUpdatedAppointment = appointmentsServiceImplementation.updateAppointment(appointment, cnp, idDoctor);    //validarile si verificarile de existenta sunt prezente in serviciu : validare campuri, existenta altui user cu email identic etc
                return new ResponseEntity(newUpdatedAppointment, HttpStatus.NO_CONTENT); //la update nu e necesar sa intorc ceva corp
            }
        }
        catch (ConflictException conflictException) { return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT); }
    }

}