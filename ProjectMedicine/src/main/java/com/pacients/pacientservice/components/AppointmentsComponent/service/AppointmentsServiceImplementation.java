package com.pacients.pacientservice.components.AppointmentsComponent.service;

import com.pacients.pacientservice.components.AppointmentsComponent.controller.AppointmentsController;
import com.pacients.pacientservice.components.AppointmentsComponent.hateoas.AppointmentsHateoas;
import com.pacients.pacientservice.components.AppointmentsComponent.hateoas.Custom.AppointmentsHateoasSelfParentCustom;
import com.pacients.pacientservice.components.AppointmentsComponent.hateoas.Parent.AppointmentsHateoasParent;
import com.pacients.pacientservice.components.AppointmentsComponent.hateoas.PatientOrDoctorInfo.AppointmentsHateoasPatientOrDoctorRequest;
import com.pacients.pacientservice.components.AppointmentsComponent.hateoas.SelfParent.AppointmentsHateoasSelfParent;
import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.AppointmentsComponent.model.repository.AppointmentsRepository;
import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.DoctorsComponent.service.DoctorsServiceImplementation;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import com.pacients.pacientservice.components.PatientsComponent.service.PatientsServiceImplementation;
import com.pacients.pacientservice.utils.Exceptions.ConflictException;
import com.pacients.pacientservice.utils.Exceptions.NotAcceptableException;
import com.pacients.pacientservice.utils.Exceptions.NotFoundException;
import com.pacients.pacientservice.utils.Exceptions.UnprocessableContentException;
import com.pacients.pacientservice.utils.Validators.AppointmentTime.AppointmentsAppointmentTimeValidator.AppointmentsAppointmentTimeValidator;
import com.pacients.pacientservice.utils.Validators.AppointmentTime.PatientAppointmentTimeValidator.PatientsAppointmentTimeValidator;
import com.pacients.pacientservice.utils.Validators.Cnp.CnpValidator;
import com.pacients.pacientservice.utils.Validators.StatusAppointment.StatusValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@Service
public class AppointmentsServiceImplementation implements AppointmentsService {
    @Autowired
    private AppointmentsRepository appointmentsRepository;
    @Autowired
    private PatientsServiceImplementation patientsServiceImplementation;
    @Autowired
    private DoctorsServiceImplementation doctorsServiceImplementation;
    private final PatientsAppointmentTimeValidator patientsAppointmentTimeValidator = new PatientsAppointmentTimeValidator();
    private final StatusValidator statusValidator = new StatusValidator();
    private final CnpValidator cnpValidator = new CnpValidator();
    @Autowired
    private AppointmentsHateoasParent appointmentsHateoasParent;
    @Autowired
    private AppointmentsHateoasSelfParent appointmentsHateoasSelfParent;
    @Autowired
    private AppointmentsHateoasSelfParentCustom appointmentsHateoasSelfParentCustom;
    @Autowired
    private AppointmentsHateoasPatientOrDoctorRequest appointmentsHateoasPatientOrDoctorRequest;

    /*
         NAME METHOD: getAllAppointments from class AppointmentsServiceImplementation
         SCOPE: Retrieve all appointments information with HATEOAS links.
      */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAllAppointments() {
        log.info("getAllAppointments() from AppointmentsServiceImplementation");
        /* Appointment resource + SelfParent Hateoas */
        List<EntityModel<AppointmentsTable>> listApppointments = new ArrayList<>();

        /* Find appointment resource and associate each data with hateoas component */
        List<AppointmentsTable> appointments = appointmentsRepository.findAll();
        for (AppointmentsTable appointment : appointments) {
            EntityModel<AppointmentsTable> entityModel = appointmentsHateoasSelfParent.toModel(appointment);
            listApppointments.add(entityModel);
        }
        return CollectionModel.of(listApppointments);
    }


    /*
     * NAME METHOD: getAppointmentsByStatus from class AppointmentsServiceImplementation
     * SCOPE: Retrieve appointments information by status with HATEOAS links.
     * HTTP CODES:
     *   - 422 -> Unprocessable Entity
     *           Invalid status syntax results in a 422 response.
     */

    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsByStatus(String status) {
        log.info("getAppointmentsByStatus() from AppointmentsServiceImplementation");

        if (!statusValidator.isValid(status, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<AppointmentsTable> appointments = appointmentsRepository.findAppointmentsTableByStatusContaining(status);
        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();
        appointmentForCustomHateoas.setStatus(status);

        return getBodyWithHateoas(listAppointments, appointmentForCustomHateoas);

    }

    /*
      NAME METHOD: getAppointmentsForSpecificPatient from class AppointmentsServiceImplementation
      REST OF EXPLANATIONS: same as getAppointmentsByStatus
   */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForSpecificPatient(String cnp) {
        log.info("getAppointmentsForSpecificPatient() from AppointmentsServiceImplementation");

        if (!cnpValidator.isValid(cnp, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<AppointmentsTable> appointments = appointmentsRepository.findAppointmentsTableByAppointmentsTablePK_Cnp(cnp);
        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());
        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();

        return getBodyWithHateoasPatientOrDoctorCase(listAppointments, appointmentForCustomHateoas);

    }

    /*
      NAME METHOD: getAppointmentsForPatientByAppointmentTime from class AppointmentsServiceImplementation
      REST OF EXPLANATIONS: same as getAppointmentsByStatus
   */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForPatientByAppointmentTime(String cnp, Integer date, String type) {
        log.info("getAppointmentsForPatientByAppointmentTime() from AppointmentsServiceImplementation");

        List<AppointmentsTable> appointments = new ArrayList<>();
        if (!cnpValidator.isValid(cnp, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        if (!type.isEmpty()) {
            if (type.equals("day")) {
                appointments = appointmentsRepository.findAppointmentsByDay(date);
            } else if (type.equals("month")) {
                appointments = appointmentsRepository.findAppointmentsByMonth(date);
            } else {
                throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
            }
        } else {
            appointments = appointmentsRepository.findAppointmentsTableByAppointmentsTablePK_Cnp(cnp);
        }

        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();

        return getBodyWithHateoasPatientOrDoctorCase(listAppointments, appointmentForCustomHateoas);
    }

    /*
     NAME METHOD: getAppointmentsForPatientByStatus from class AppointmentsServiceImplementation
     REST OF EXPLANATIONS: same as getAppointmentsByStatus
  */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForPatientByStatus(String cnp, String status) {
        log.info("getAppointmentsForPatientByStatus() from AppointmentsServiceImplementation");

        if (!statusValidator.isValid(status, null) || !statusValidator.isValid(status, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<AppointmentsTable> appointments = appointmentsRepository.findAppointmentsTableByStatusContaining(status);
        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();

        return getBodyWithHateoasPatientOrDoctorCase(listAppointments, appointmentForCustomHateoas);

    }

    /*
        NAME METHOD: getAppointmentsForSpecificDoctor from class AppointmentsServiceImplementation
        REST OF EXPLANATIONS: same as getAppointmentsByStatus
     */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForSpecificDoctor(Integer idDoctor) {
        log.info("getAppointmentsForSpecificDoctor() from AppointmentsServiceImplementation");

        List<AppointmentsTable> appointments = appointmentsRepository.findAppointmentsTableByAppointmentsTablePK_IdDoctor(idDoctor);
        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();

        return getBodyWithHateoasPatientOrDoctorCase(listAppointments, appointmentForCustomHateoas);

    }

    /*
       NAME METHOD: getAppointmentsForDoctorByAppointmentTime from class AppointmentsServiceImplementation
       REST OF EXPLANATIONS: same as getAppointmentsByStatus
    */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForDoctorByAppointmentTime(Integer idDoctor, Integer date, String type) {
        log.info("getAppointmentsForDoctorByAppointmentTime() from AppointmentsServiceImplementation");

        List<AppointmentsTable> appointments = new ArrayList<>();

        if (!type.isEmpty()) {
            if (type.equals("day")) {
                appointments = appointmentsRepository.findAppointmentsByDay(date);
            } else {
                appointments = appointmentsRepository.findAppointmentsByMonth(date);
            }
        } else {
            appointments = appointmentsRepository.findAppointmentsTableByAppointmentsTablePK_IdDoctor(idDoctor);
        }
        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();

        return getBodyWithHateoasPatientOrDoctorCase(listAppointments, appointmentForCustomHateoas);
    }

    /*
       NAME METHOD: getAppointmentsForDoctorByStatus from class AppointmentsServiceImplementation
       REST OF EXPLANATIONS: same as getAppointmentsByStatus
    */
    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForDoctorByStatus(Integer idDoctor, String status) {
        log.info("getAppointmentsForDoctorByStatus() from AppointmentsServiceImplementation");

        if (!statusValidator.isValid(status, null) || !statusValidator.isValid(status, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<AppointmentsTable> appointments = appointmentsRepository.findAppointmentsTableByStatusContaining(status);
        List<EntityModel<AppointmentsTable>> listAppointments = appointments.stream()
                .map(appointmentsHateoasSelfParent::toModel)
                .collect(Collectors.toList());


        AppointmentsTable appointmentForCustomHateoas = new AppointmentsTable();

        return getBodyWithHateoasPatientOrDoctorCase(listAppointments, appointmentForCustomHateoas);
    }


    /*
       NAME METHOD: addAppointment from class AppointmentsServiceImplementation
       SCOPE: Add a new appointment with input validation and potential conflict checks.

       HTTP CODES:
         *** 406 -> Not Acceptable
             - Returned if there are missing or invalid input data.

         *** 422 -> Unprocessable Entity
             - Returned if input field validation fails.

         *** 409 -> Conflict
             - Returned if there is an attempt to add a appointment with the same  id.

    */
    @Override
    public EntityModel<AppointmentsTable> addAppointment(AppointmentsTable appointmentsTable) {
        log.info("addAppointment() from AppointmentsServiceImplementation");

            /*
           If there are missing or invalid input data, returning HTTP status code 406 (Not Acceptable)
           is appropriate, as it signifies that the server cannot process and respond to the client's
           request due to the absence or incorrectness of the provided data.
        */
        if (
                null == appointmentsTable.getAppointmentsTablePK().getCnp()
                        || null == appointmentsTable.getAppointmentsTablePK().getIdDoctor()
                        || null == appointmentsTable.getAppointmentsTablePK().getAppointmentTime()
                        || null == appointmentsTable.getStatus()
        ) {
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }

          /*
           Validate input fields using respective validators. If any of the validations fail,
           throw an UnprocessableContentException with a specific message indicating the fields
           that did not pass the validation.
        */
        String formattedAppointmentTime = formatYYYYMMddHHmm(appointmentsTable.getAppointmentsTablePK().getAppointmentTime());
        if (
                !cnpValidator.isValid(appointmentsTable.getAppointmentsTablePK().getCnp(), null)
                        || !patientsAppointmentTimeValidator.isValid(formattedAppointmentTime, null)
                        || !statusValidator.isValid(appointmentsTable.getStatus(), null)
        ) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        /*
           Check for an existing patient with the same email but a different CNP.
           If found, throw a ConflictException with HTTP status code 409.
        */
        AppointmentsTable duplicateAppointment = appointmentsRepository.findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(appointmentsTable.getAppointmentsTablePK().getCnp(), appointmentsTable.getAppointmentsTablePK().getIdDoctor(), appointmentsTable.getAppointmentsTablePK().getAppointmentTime());
        if (null != duplicateAppointment) {
            throw new ConflictException(RESOURCE_ALREADY_EXIST);
        }
        try {
            PatientsTable patientsTable = patientsServiceImplementation.getPatientByCnp(appointmentsTable.getAppointmentsTablePK().getCnp()).getContent();
            DoctorsTable doctorsTable = doctorsServiceImplementation.getDoctorByIdDoctor(appointmentsTable.getAppointmentsTablePK().getIdDoctor()).getContent();
            appointmentsTable.setPatient(patientsTable);
            appointmentsTable.setDoctor(doctorsTable);
            appointmentsRepository.save(appointmentsTable);
        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException(SAVE_ERROR + dataAccessException.getRootCause());
        }

        appointmentsTable = appointmentsRepository
                .findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(
                        appointmentsTable.getAppointmentsTablePK().getCnp(),
                        appointmentsTable.getAppointmentsTablePK().getIdDoctor(),
                        appointmentsTable.getAppointmentsTablePK().getAppointmentTime()
                );
        return appointmentsHateoasSelfParent.toModel(appointmentsTable);
    }

    /*
        NAME METHOD: updateAppointment from class AppointmentsServiceImplementation
        SCOPE: Update an existing appointment with input validation.

        HTTP CODES:
          *** 404 -> Not Found
              - Returned if the appointment to be updated is not found.

          *** 422 -> Unprocessable Entity
              - Returned if there are missing or invalid input data.

          *** 406 -> Not Acceptable
              - Returned if there are missing or invalid input data during  update.

     */
    @Override
    public EntityModel<AppointmentsTable> updateAppointment(AppointmentsTable appointmentsTable, String cnp, Integer idDoctor) {
        log.info("updateAppointment() from AppointmentsServiceImplementation");

        AppointmentsTable existingAppointment = appointmentsRepository.findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(appointmentsTable.getAppointmentsTablePK().getCnp(), appointmentsTable.getAppointmentsTablePK().getIdDoctor(), appointmentsTable.getAppointmentsTablePK().getAppointmentTime());
        /* Check resource to be existent */
        if (null == existingAppointment) {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }
          /*
       If there are missing or invalid input data, returning HTTP status code 406 (Not Acceptable)
       is appropriate, as it signifies that the server cannot process and respond to the client's
       request due to the absence or incorrectness of the provided data.
        */
        if (
                null == appointmentsTable.getAppointmentsTablePK().getCnp()
                        || null == appointmentsTable.getAppointmentsTablePK().getIdDoctor()
                        || null == appointmentsTable.getAppointmentsTablePK().getAppointmentTime()
                        || null == appointmentsTable.getStatus()
        ) {
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }

        String formattedAppointmentTime = formatYYYYMMddHHmm(appointmentsTable.getAppointmentsTablePK().getAppointmentTime());
        /* Check fields validity .*/
        if (!cnpValidator.isValid(appointmentsTable.getAppointmentsTablePK().getCnp(), null)
                || !patientsAppointmentTimeValidator.isValid(formattedAppointmentTime, null)
                || !statusValidator.isValid(appointmentsTable.getStatus(), null)
        ) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        try {

            PatientsTable patientsTable = patientsServiceImplementation.getPatientByCnp(appointmentsTable.getAppointmentsTablePK().getCnp()).getContent();
            DoctorsTable doctorsTable = doctorsServiceImplementation.getDoctorByIdDoctor(appointmentsTable.getAppointmentsTablePK().getIdDoctor()).getContent();
            appointmentsRepository.delete(appointmentsTable);
            appointmentsTable.setPatient(patientsTable);
            appointmentsTable.setDoctor(doctorsTable);
            appointmentsRepository.save(appointmentsTable);


        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException(UPDATE_ERROR + dataAccessException.getRootCause());
        }

        appointmentsTable = appointmentsRepository.findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(
                appointmentsTable.getAppointmentsTablePK().getCnp(),
                appointmentsTable.getAppointmentsTablePK().getIdDoctor(),
                appointmentsTable.getAppointmentsTablePK().getAppointmentTime());
        return appointmentsHateoasSelfParent.toModel(appointmentsTable);
    }


    @Override
    public CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsByNumberPage(Integer page, Integer itemsPerPage) {
        log.info("getAppointmentsByNumberPage() from AppointmentsServiceImplementation");

        List<AppointmentsTable> listPageableAppointments = appointmentsRepository.findAll();
        Integer index_start = page * itemsPerPage;
        Integer index_stop = index_start + itemsPerPage;

        List<EntityModel<AppointmentsTable>> listResult = new ArrayList<>();
        for (Integer index = index_start; index < index_stop; index++) {
            if (index < listPageableAppointments.size())
                listResult.add(appointmentsHateoasSelfParent.toModel(listPageableAppointments.get(index)));
        }
        Link selfLink = linkTo(methodOn(AppointmentsController.class).
                getAllAppointments(Optional.of(page), Optional.of(itemsPerPage), Optional.empty()))
                .withSelfRel();

        Optional<Integer> nextPage = Optional.of(page + 1);
        Optional<Integer> prevPage = Optional.of(page - 1);

        Link nextLink = linkTo(methodOn(AppointmentsController.class).
                getAllAppointments(nextPage, Optional.of(itemsPerPage), Optional.empty()))
                .withRel("next");

        Link prevLink = linkTo(methodOn(AppointmentsController.class).
                getAllAppointments(prevPage, Optional.of(itemsPerPage), Optional.empty()))
                .withRel("prev");

        Link parentLink = linkTo(methodOn(AppointmentsController.class).
                getAllAppointments(Optional.empty(), Optional.empty(), Optional.empty()))
                .withRel("parent");

        // nu sunt elemente pe pagina solicitata
        if (listResult.size() == 0 && page != 0) {
            prevPage = Optional.of(listPageableAppointments.size() / itemsPerPage - 1);
            prevLink = linkTo(methodOn(AppointmentsController.class).
                    getAllAppointments(prevPage, Optional.of(itemsPerPage), Optional.empty()))
                    .withRel("prev");
            return CollectionModel.of(listResult, parentLink, prevLink);
        }

        if (page == 0) {
            CollectionModel<EntityModel<AppointmentsTable>> result = CollectionModel.of(listResult, selfLink, parentLink, nextLink);
            return result;
        }

        if (page == (listPageableAppointments.size() / itemsPerPage - 1)) {
            CollectionModel<EntityModel<AppointmentsTable>> result = CollectionModel.of(listResult, selfLink, parentLink, prevLink);
            return result;
        }

        CollectionModel<EntityModel<AppointmentsTable>> result = CollectionModel.of(listResult, selfLink, parentLink, nextLink, prevLink);
        return result;
    }

    public ResponseEntity<?> provideHeateoasForPageable(Integer itemsPerPage) {
        return appointmentsHateoasSelfParent.provideHeateoasForPageable(itemsPerPage);
    }


    /*-----UTILS------*/
    public CollectionModel<EntityModel<AppointmentsTable>> intersectFilteredPatientAppointments(
            CollectionModel<EntityModel<AppointmentsTable>> result1,
            CollectionModel<EntityModel<AppointmentsTable>> result2) {

        List<EntityModel<AppointmentsTable>> list1 = new ArrayList<>(result1.getContent());
        List<EntityModel<AppointmentsTable>> list2 = new ArrayList<>(result2.getContent());

        // Retine doar elementele comune intre cele doua liste
        list1.retainAll(list2);

        /* Union over links */
        result1.getLinks().forEach(link -> {
            if (!result2.getLinks().contains(link)) {
                result2.add(link);
            }
        });

        return CollectionModel.of(list1, result2.getLinks());
    }

    private CollectionModel<EntityModel<AppointmentsTable>> getBodyWithHateoas(List<EntityModel<AppointmentsTable>> listAppointments, AppointmentsTable appointmentForCustomHateoas) {
        Links customHateoasParent = appointmentsHateoasParent.toModel(new AppointmentsTable()).getLinks();
        Links customHateoasSelfParent = appointmentsHateoasSelfParentCustom.toModel(appointmentForCustomHateoas).getLinks();

        if (listAppointments.isEmpty()) {
            return CollectionModel.of(listAppointments, customHateoasParent);
        } else {
            return CollectionModel.of(listAppointments, customHateoasSelfParent);
        }
    }

    private CollectionModel<EntityModel<AppointmentsTable>> getBodyWithHateoasPatientOrDoctorCase(List<EntityModel<AppointmentsTable>> listAppointments, AppointmentsTable appointmentForCustomHateoas) {
        Links customHateoasParent = appointmentsHateoasParent.toModel(new AppointmentsTable()).getLinks();
        Links customHateoasSelfParent = appointmentsHateoasPatientOrDoctorRequest.toModel(appointmentForCustomHateoas).getLinks();

        if (listAppointments.isEmpty()) {
            return CollectionModel.of(listAppointments, customHateoasParent);
        } else {
            return CollectionModel.of(listAppointments, customHateoasSelfParent);
        }
    }

    private String formatYYYYMMddHHmm(LocalDateTime appointmentTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return appointmentTime.format(formatter);
    }


}