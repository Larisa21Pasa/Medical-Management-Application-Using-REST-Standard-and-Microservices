package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.service.Consultations;

import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.controller.ConsultationsController;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.hateoas.Custom.ConsultationsHateoasSelfParentCustom;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.hateoas.Parent.ConsultationsHateoasParent;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.hateoas.SelfAndParent.ConsultationsHateoasSelfParent;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.ConsultationsCollection;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.InvestigationsCollection;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.repository.ConsultationsRepository;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.ConflictException;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.NotAcceptableException;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.NotFoundException;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.UnprocessableContentException;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.AppointmentsAppointmentTimeValidator.AppointmentsAppointmentTimeValidator;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.Diagnostic.DiagnosticValidator;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.IDPatient.IDPatientValidator;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.ProcessingDuration.ProcessingDurationValidator;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.Result.ResultValidator;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.String.StringValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Logs.ConsultationsLog.NOT_FOUND_EXCEPTION;
import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Others.Macros.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
@Log4j2
public class ConsultationsServiceImplementation implements ConsultationsService{
    @Autowired
    private ConsultationsRepository consultationsRepository;
    @Autowired
    private ConsultationsHateoasSelfParent consultationsHateoasSelfParent;
    @Autowired
    private ConsultationsHateoasSelfParentCustom consultationsHateoasSelfParentCustom;
    @Autowired
    private ConsultationsHateoasParent consultationsHateoasParent;
    private DiagnosticValidator diagnosticValidator = new DiagnosticValidator();
    private ProcessingDurationValidator processingDurationValidator = new ProcessingDurationValidator();
    private StringValidator stringValidator = new StringValidator();
    private IDPatientValidator idPatientValidator = new IDPatientValidator();
    private AppointmentsAppointmentTimeValidator appointmentsAppointmentTimeValidator = new AppointmentsAppointmentTimeValidator();
    private ResultValidator resultValidator = new ResultValidator();

    private final MongoTemplate mongoTemplate;

    public ConsultationsServiceImplementation(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public void saveInvestigation(InvestigationsCollection investigation) {
        mongoTemplate.save(investigation);
    }

    /*--------GET------------*/
    /*
         NAME METHOD: getAllConsultations from class ConsultationsServiceImplementation
         SCOPE: Retrieve all consultations information with HATEOAS links.
      */
    @Override
    public CollectionModel<EntityModel<ConsultationsCollection>>  getAllConsultations() {
        log.info("getAllConsultations() from ConsultationsServiceImplementation");

        /* Doctor resource + SelfParent Hateoas */
        List<EntityModel<ConsultationsCollection>> listConsultations = new ArrayList<>();

        /* Find Doctor resource and associate each data with hateoas component */
        List<ConsultationsCollection> consultations = consultationsRepository.findAll();
        for (ConsultationsCollection consultation : consultations) {
            EntityModel<ConsultationsCollection> entityModel = consultationsHateoasSelfParent.toModel(consultation);
            listConsultations.add(entityModel);
        }

        return CollectionModel.of(listConsultations);
    }

    /*
     NAME METHOD: getCollectionById from class ConsultationsServiceImplementation
     SCOPE: Retrieve consultation information by _id with HATEOAS links.

     HTTP CODES:
           *** 404 -> Not Found
           - No consultation found for the given _id triggers a 404 response.
  */
    @Override
    public EntityModel<ConsultationsCollection> getCollectionById(String _id) {
        log.info("getCollectionById() from ConsultationsServiceImplementation");

        ConsultationsCollection consultation = consultationsRepository.findConsultationsCollectionBy_id(_id);
        if (null == consultation) {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        } else {
            return consultationsHateoasSelfParent.toModel(consultation);
        }
    }

    /*
     * NAME METHOD: getConsultationsByIdPatient from class ConsultationsServiceImplementation
     * SCOPE: Retrieve consultation information by status with HATEOAS links.
     * HTTP CODES:
     *   - 422 -> Unprocessable Entity
     *           Invalid status syntax results in a 422 response.
     */
    @Override
    public CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByIdPatient(String idPatient) {
        log.info("getConsultationsByIdPatient() from ConsultationsServiceImplementation");

        if(!idPatientValidator.isValid(idPatient, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<ConsultationsCollection> consultations = consultationsRepository.findConsultationsCollectionsByIdPatient(idPatient);
        List<EntityModel<ConsultationsCollection>> listConsultations = consultations.stream()
                .map(consultationsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        ConsultationsCollection consultationForCustomHateoas  = new ConsultationsCollection();
        consultationForCustomHateoas.setIdPatient(idPatient);

        return getBodyWithHateoas(listConsultations,consultationForCustomHateoas);

    }


    /*
     * NAME METHOD: getConsultationsByIdDoctor from class ConsultationsServiceImplementation
     * SCOPE: Retrieve consultation information by status with HATEOAS links.
     * HTTP CODES:
     *   - 422 -> Unprocessable Entity
     *           Invalid status syntax results in a 422 response.
     */
    @Override
    public CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByIdDoctor(Integer idDoctor) {
        log.info("getConsultationsByIdDoctor() from ConsultationsServiceImplementation");

        List<ConsultationsCollection> consultations = consultationsRepository.findConsultationsCollectionsByIdDoctor(idDoctor);

        List<EntityModel<ConsultationsCollection>> listConsultations = consultations.stream()
                .map(consultationsHateoasSelfParent::toModel)
                .collect(Collectors.toList());
        log.info("listConsultations {} ", listConsultations);

        ConsultationsCollection consultationForCustomHateoas  = new ConsultationsCollection();
        consultationForCustomHateoas.setIdDoctor(idDoctor);

        return getBodyWithHateoas(listConsultations,consultationForCustomHateoas);
    }

    /*
      NAME METHOD: getConsultationsByAppointmentPK from class ConsultationsServiceImplementation
      REST OF EXPLANATIONS: same as getConsultationsByIdDoctor
   */
    @Override
    public CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByAppointmentPK(LocalDateTime appointmentTime) {
        log.info("getConsultationsByAppointmentPK() from ConsultationsServiceImplementation");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedAppointmentTime = appointmentTime.format(formatter);

        if (!appointmentsAppointmentTimeValidator.isValid(formattedAppointmentTime, null)) {
            throw new UnprocessableContentException("Appointment time is in an invalid format. Please try again!");
        }

        List<ConsultationsCollection> consultations = consultationsRepository.findConsultationsCollectionsByAppointmentTime(appointmentTime);
        List<EntityModel<ConsultationsCollection>> listConsultations = consultations.stream()
                .map(consultationsHateoasSelfParent::toModel)
                .collect(Collectors.toList());
        ConsultationsCollection consultationForCustomHateoas  = new ConsultationsCollection();
        consultationForCustomHateoas.setAppointmentTime(appointmentTime);

        return getBodyWithHateoas(listConsultations,consultationForCustomHateoas);

    }

    /*
     NAME METHOD: getConsultationsByDiagnostic from class ConsultationsServiceImplementation
     REST OF EXPLANATIONS: same as getConsultationsByIdDoctor
  */
    @Override
    public CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByDiagnostic(String diagnostic) {
        log.info("getConsultationsByDiagnostic() from ConsultationsServiceImplementation");

        log.info("service getConsultationsByDiagnostic ... ");
        List<ConsultationsCollection> consultations = consultationsRepository.findConsultationsCollectionByDiagnostic(diagnostic);

        List<EntityModel<ConsultationsCollection>> listConsultations = consultations.stream()
                .map(consultationsHateoasSelfParent::toModel)
                .collect(Collectors.toList());
        ConsultationsCollection consultationForCustomHateoas  = new ConsultationsCollection();
        consultationForCustomHateoas.setDiagnostic(diagnostic);

        return getBodyWithHateoas(listConsultations,consultationForCustomHateoas);

    }



    /*--------POST------------*/
  /*
       NAME METHOD: addConsultation from class ConsultationsServiceImplementation
       SCOPE: Add a new consultation with input validation and potential conflict checks.

       HTTP CODES:
         *** 406 -> Not Acceptable
             - Returned if there are missing or invalid input data.

         *** 422 -> Unprocessable Entity
             - Returned if input field validation fails.

         *** 409 -> Conflict
             - Returned if there is an attempt to add a consultation with the same appointment.

    */
    @Override
    public EntityModel<ConsultationsCollection> addConsultation(ConsultationsCollection consultation) {
        log.info("addConsultation() from ConsultationsServiceImplementation");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedAppointmentTime = consultation.getAppointmentTime().format(formatter);

        if(
                null == consultation.getIdPatient()
                || 0 == consultation.getIdDoctor()
                || null == consultation.getAppointmentTime()
                || null == consultation.getDiagnostic()
                || null == consultation.getInvestigations()
        )
        {
            throw new NotAcceptableException(NOT_ACCEPTABLE_REQUEST);
        }

        if (
                !idPatientValidator.isValid(consultation.getIdPatient(), null)
                || !appointmentsAppointmentTimeValidator.isValid(formattedAppointmentTime, null)
                || !diagnosticValidator.isValid(consultation.getDiagnostic(), null)
                || !isValidInvestigationForCreate(consultation.getInvestigations())
        )
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_MESSAGE_FIELDS);
        } else {
            log.info("[{}] -> Validate successfully, addConsultation: _id{}, idPatient:{}, idDoctor:{}, appointmentTime:{},diagnostic:{}, investigations:{}",
                    this.getClass().getSimpleName(), consultation.get_id(),consultation.getIdPatient(),consultation.getIdDoctor(),consultation.getAppointmentTime(),consultation.getDiagnostic(),consultation.getInvestigations());
        }

        //verificare conflict in database
        ConsultationsCollection duplicateConsultation = consultationsRepository.findConsultationsCollectionByIdDoctorAndIdPatientAndAppointmentTime(consultation.getIdDoctor(),consultation.getIdPatient(),consultation.getAppointmentTime());
        if(  null != duplicateConsultation  )
        {
            throw new ConflictException(CONSULTATION_WITH_THIS_APPOINTMENT_ALREADY_EXIST);
        }

        try {
            consultationsRepository.save(consultation);
        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException("Error when add consultation to database  {}", dataAccessException.getRootCause());
        }

        //extract consultation to get back to http response
        consultation = consultationsRepository
                .findConsultationsCollectionBy_id(
                        consultation.get_id()
                );
        return consultationsHateoasSelfParent.toModel(consultation);


    }


    /*
        NAME METHOD: addInvestigation from class ConsultationsServiceImplementation
        REST OF EXPLANATIONS: same as addConsultation
     */
    @Override
    public EntityModel<ConsultationsCollection> addInvestigation(ConsultationsCollection consultation) {
        log.info("addInvestigation() from ConsultationsServiceImplementation");

        if( null == consultation.getInvestigations() )
        {
            throw new NotAcceptableException(NOT_ACCEPTABLE_REQUEST);
        }

        if ( !isValidInvestigationForCreate(consultation.getInvestigations()) )
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_MESSAGE_FIELDS);
        }


        try {
            ConsultationsCollection existingConsultation = consultationsRepository.findConsultationsCollectionBy_id(consultation.get_id());
            List<InvestigationsCollection> existingInvestigations  = existingConsultation.getInvestigations();
            existingInvestigations.add(consultation.getInvestigations().get(0));


            existingConsultation.setInvestigations(existingInvestigations);

            consultationsRepository.save(existingConsultation);
        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException("Error when add consultation to database {}", dataAccessException.getRootCause());
        }

        //extract investigation to get back to http response
        consultation = consultationsRepository
                .findConsultationsCollectionBy_id(
                        consultation.get_id()
                );
        return consultationsHateoasSelfParent.toModel(consultation);


    }

    /*--------PATCH------------*/
   /*
        NAME METHOD: updateConsultation from class ConsultationsServiceImplementation
        SCOPE: Update an existing consultation with input validation.

        HTTP CODES:
          *** 404 -> Not Found
              - Returned if the consultation to be updated is not found.

          *** 422 -> Unprocessable Entity
              - Returned if there are missing or invalid input data.

          *** 406 -> Not Acceptable
              - Returned if there are missing or invalid input data during the  update.

     */
    @Override
    public EntityModel<ConsultationsCollection> updateConsultation(ConsultationsCollection consultation, String  objectId){
        log.info("updateConsultation() from ConsultationsServiceImplementation");

        if(null == consultationsRepository.findConsultationsCollectionBy_id(consultation.get_id()))
        {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }

        ConsultationsCollection updatedConsultation =  consultationsRepository.findConsultationsCollectionBy_id(objectId);
        if ((consultation.getIdPatient() != null) ) {
            if(idPatientValidator.isValid(consultation.getIdPatient(), null))
            {
                updatedConsultation.setIdPatient(consultation.getIdPatient());
            }
            else
            {
                throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_MESSAGE_FIELDS);
            }
        }

        if (consultation.getIdDoctor() != 0) {
            updatedConsultation.setIdDoctor(consultation.getIdDoctor());
        }

        if (consultation.getAppointmentTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedAppointmentTime = consultation.getAppointmentTime().format(formatter);
            if(appointmentsAppointmentTimeValidator.isValid(formattedAppointmentTime, null))
            {
                updatedConsultation.setAppointmentTime(consultation.getAppointmentTime());
            }
            else
            {
                throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_MESSAGE_FIELDS);
            }
        }

        if ((consultation.getDiagnostic() != null) ) {

            if(diagnosticValidator.isValid(consultation.getDiagnostic(), null))
            {
                updatedConsultation.setDiagnostic(consultation.getDiagnostic());
            }
            else
            {
                throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_MESSAGE_FIELDS);
            }
        }

        if (consultation.getInvestigations() != null) {
            if (!isValidInvestigationForUpdate(consultation.getInvestigations())) {
                throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_MESSAGE_FIELDS);
            }
            List<InvestigationsCollection> updatedInvestigations = updatedConsultation.getInvestigations();
            UpdateInvestigations(updatedInvestigations,consultation);
            updatedConsultation.setInvestigations(updatedInvestigations);
        }

        try {
            consultationsRepository.save(updatedConsultation);
        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException("Error when update consultation {}", dataAccessException.getRootCause());
        }

        consultation = consultationsRepository
                .findConsultationsCollectionBy_id(
                        consultation.get_id()
                );
        return consultationsHateoasSelfParent.toModel(consultation);
    }

    /*
      NAME METHOD: UpdateInvestigations from class ConsultationsServiceImplementation
      REST OF EXPLANATIONS: same as updateConsultation
   */
    private void UpdateInvestigations(List<InvestigationsCollection> updatedInvestigations, ConsultationsCollection consultation)
    {
        log.info("UpdateInvestigations() from ConsultationsServiceImplementation");

        for (InvestigationsCollection updatedInvestigation : consultation.getInvestigations()) {

            String updatedInvestigationId = updatedInvestigation.get_id();

            updatedInvestigations.stream()
                    .filter(investigation -> investigation.get_id().equals(updatedInvestigationId))
                    .findFirst()
                    .ifPresent(modifiedInvestigation -> {
                        if ((updatedInvestigation.getNaming() != null)) {

                            modifiedInvestigation.setNaming(updatedInvestigation.getNaming());

                        }
                        if ((updatedInvestigation.getProcessingHours() != 0)) {

                            modifiedInvestigation.setProcessingHours(updatedInvestigation.getProcessingHours());

                        }
                        if ((updatedInvestigation.getResult() != null)) {

                            modifiedInvestigation.setResult(updatedInvestigation.getResult());

                        }
                    });

        }
    }
    @Override
    public EntityModel<ConsultationsCollection> deleteConsultation(String _id) {
        log.info("deleteConsultation() from ConsultationsServiceImplementation");

        ConsultationsCollection consultationsCollection = consultationsRepository.findConsultationsCollectionBy_id(_id);
        if(null == consultationsCollection)
        {
            throw new NotFoundException();
        }
        EntityModel deletedConsultation = consultationsHateoasParent.toModel(consultationsCollection);
        try {
            consultationsRepository.deleteById(consultationsCollection.get_id());
            return deletedConsultation;
        }catch (DataAccessException dataAccessException) {
            throw new RuntimeException("Error when delete consultation: {}", dataAccessException.getRootCause());
        }
    }


    @Override
    public CollectionModel<EntityModel<ConsultationsCollection>> getConsultationsByNumberPage(Integer page, Integer itemsPerPage) {
        log.info("getConsultationsByNumberPage() from ConsultationsServiceImplementation");

        List<ConsultationsCollection> listPageableConsultations = consultationsRepository.findAll();
        Integer index_start = page * itemsPerPage ;
        Integer index_stop = index_start + itemsPerPage ;

        List<EntityModel<ConsultationsCollection>> listResult = new ArrayList<>();
        for(Integer index = index_start; index < index_stop; index++)
        {
            if(index < listPageableConsultations.size())
                listResult.add(consultationsHateoasSelfParent.toModel(listPageableConsultations.get(index)));
        }
        Link selfLink = linkTo(methodOn(ConsultationsController.class)
                .getAllConsultations(Optional.of(page), Optional.of(itemsPerPage), Optional.empty(),Optional.empty(),Optional.empty(), Optional.empty()))
                .withSelfRel();

        Optional<Integer> nextPage = Optional.of(page + 1);
        Optional<Integer> prevPage = Optional.of(page - 1);

        Link nextLink = linkTo(methodOn(ConsultationsController.class)
                .getAllConsultations(nextPage, Optional.of(itemsPerPage), Optional.empty(), Optional.empty(),Optional.empty(), Optional.empty()))
                .withRel("next");

        Link prevLink = linkTo(methodOn(ConsultationsController.class)
                .getAllConsultations(prevPage, Optional.of(itemsPerPage), Optional.empty(),Optional.empty(),  Optional.empty(), Optional.empty()))
                .withRel("prev");

        Link parentLink = linkTo(methodOn(ConsultationsController.class)
                .getAllConsultations(Optional.empty(), Optional.empty(),Optional.empty(),  Optional.empty(), Optional.empty(), Optional.empty()))
                .withRel("parent");

        // nu sunt elemente pe pagina solicitata
        if (listResult.size() == 0 && page != 0)
        {
            prevPage = Optional.of(listPageableConsultations.size() / itemsPerPage - 1);
            prevLink = linkTo(methodOn(ConsultationsController.class)
                    .getAllConsultations(prevPage, Optional.of(itemsPerPage), Optional.empty(), Optional.empty(),Optional.empty(), Optional.empty()))
                    .withRel("prev");
            return CollectionModel.of(listResult, parentLink, prevLink);
        }

        if (page == 0)
        {
            CollectionModel<EntityModel<ConsultationsCollection>> result = CollectionModel.of(listResult, selfLink, parentLink, nextLink);
            return result;
        }

        if (page == (listPageableConsultations.size() / itemsPerPage - 1))
        {
            CollectionModel<EntityModel<ConsultationsCollection>> result = CollectionModel.of(listResult, selfLink, parentLink, prevLink);
            return result;
        }

        CollectionModel<EntityModel<ConsultationsCollection>> result = CollectionModel.of(listResult, selfLink, parentLink, nextLink, prevLink);
        return result;
    }
    public ResponseEntity<?> provideHeateoasForPageable(Integer itemsPerPage)
    {
        return consultationsHateoasSelfParent.provideHeateoasForPageable(itemsPerPage);
    }



    /*--------UTILS------------*/
    public CollectionModel<EntityModel<ConsultationsCollection>> intersect(
            CollectionModel<EntityModel<ConsultationsCollection>> result1,
            CollectionModel<EntityModel<ConsultationsCollection>> result2) {
        log.info("intersect result1 {} cu result 2 {} ", result1, result2);

        List<EntityModel<ConsultationsCollection>> list1 = new ArrayList<>(result1.getContent());
        List<EntityModel<ConsultationsCollection>> list2 = new ArrayList<>(result2.getContent());

        // Extract _id values for comparison
        Set<String> idSet1 = list1.stream().map(item -> item.getContent().get_id()).collect(Collectors.toSet());
        Set<String> idSet2 = list2.stream().map(item -> item.getContent().get_id()).collect(Collectors.toSet());

        // Retain only elements with common _id values
        List<EntityModel<ConsultationsCollection>> intersection = list1.stream()
                .filter(item -> idSet2.contains(item.getContent().get_id()))
                .collect(Collectors.toList());

        log.info("RESULT IN intersect {}", intersection);
        /* Union over links */
        result1.getLinks().forEach(link -> {
            if (!result2.getLinks().contains(link)) {
                result2.add(link);
            }
        });

        return CollectionModel.of(intersection, result2.getLinks());
    }


    private CollectionModel<EntityModel<ConsultationsCollection>> getBodyWithHateoas(List<EntityModel<ConsultationsCollection>> listConsultation, ConsultationsCollection consultationForCustomHateoas  ) {
        Links customHateoasParent= consultationsHateoasParent.toModel(new ConsultationsCollection()).getLinks();
        Links customHateoasSelfParent = consultationsHateoasSelfParentCustom.toModel(consultationForCustomHateoas).getLinks();
        log.info("getBodyWithHateoas ");
        if (listConsultation.isEmpty()) {
            return CollectionModel.of(listConsultation,customHateoasParent);
        } else {
            return CollectionModel.of(listConsultation, customHateoasSelfParent);
        }
    }
    private boolean isValidInvestigationForCreate(List<InvestigationsCollection> investigations) {
        for (InvestigationsCollection investigation : investigations) {
            boolean isValidNaming = stringValidator.isValid(investigation.getNaming(), null);
            boolean isValidProcessingHours = processingDurationValidator.isValid(String.valueOf(investigation.getProcessingHours()), null);
            boolean isValidResult = resultValidator.isValid(investigation.getResult(), null);
            log.info("isValidNaming={} isValidProcessingHours={} isValidResult={}", isValidNaming,isValidProcessingHours,isValidResult);
            if (!isValidNaming || !isValidProcessingHours || !isValidResult) {
                // Dacă oricare dintre validări e falsă, trimite ceva automat
                // Exemplu: throw new RuntimeException("Validare esuata pentru investigatia cu nume: " + investigation.getNaming());
                // Sau poate ai o altă logică în minte aici
                return false;
            }
        }

        // Dacă nu s-a ajuns la return în bucla for, atunci toate validările au trecut cu succes
        return true;
    }
    private boolean isValidInvestigationForUpdate(List<InvestigationsCollection> investigations) {
        for (InvestigationsCollection investigation : investigations) {
            if (investigation.getNaming() != null) {
                if(!stringValidator.isValid(investigation.getNaming(), null))
                {
                    return false;
                }
            }
            if (investigation.getProcessingHours() != 0)  {
                if(!processingDurationValidator.isValid(String.valueOf(investigation.getProcessingHours()), null))
                {
                    return false;
                }
            }
            if (investigation.getResult() != null) {
                if(!resultValidator.isValid(investigation.getResult(), null))
                {
                    return false;
                }
            }

        }

        // Dacă nu s-a ajuns la return în bucla for, atunci toate validările au trecut cu succes
        return true;
    }


}
