package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.controller;

import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.hateoas.SelfAndParent.ConsultationsHateoasSelfParent;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine.ConsultationsCollection;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.repository.ConsultationsRepository;
import com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.service.Consultations.ConsultationsServiceImplementation;
import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Exceptions.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Logs.ConsultationsLog.NOT_ACCEPTABLE_EXCEPTION;
import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Others.Macros.CONFLIC_MESSAGE_NOT_SAME_IDOBJECT;
import static com.projectmedicine.mongo.projectmedicinemongodb.Utils.Others.Macros.CONSULTATION_WITH_THIS_APPOINTMENT_ALREADY_EXIST;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/api/medical_office/consultations")
public class ConsultationsController {
    @Autowired
    private ConsultationsRepository consultationsRepository;
    @Autowired
    private ConsultationsServiceImplementation consultationsServiceImplementation;

//GET

    @Operation(summary = "Get all/searched consultation resources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - Found all/searched resources",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ConsultationsCollection.class)))}),
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
    public ResponseEntity<?> getAllConsultations(
            @RequestParam(value = "page")  @Min(0) Optional<Integer> page,
            @RequestParam(value="itemsPerPage", defaultValue = "3" )  @Min(value = 1, message = "Page should be at least 1.")Optional<Integer> itemsPerPage,
            @RequestParam(value = "idPatient") Optional<String> idPatient,
            @RequestParam(value = "idDoctor") Optional<Integer> idDoctor,
            @RequestParam(value = "diagnostic") Optional<String> diagnostic,
            @RequestParam(value = "appointmentTime")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            Optional<LocalDateTime> appointmentTime
            ) {

        log.info("[{}] -> GET, getAllConsultations: idPatient:{}, idDoctor:{}, diagnostic:{}, appointmentTime:{}", this.getClass().getSimpleName(), idPatient, idDoctor, diagnostic, appointmentTime);
        try {
            CollectionModel<EntityModel<ConsultationsCollection>> result = consultationsServiceImplementation.getAllConsultations();
    log.info("result din controller {}", result);
            if(page.isPresent())
            {
                if (page.get() < 0)
                {
                    consultationsServiceImplementation.provideHeateoasForPageable(itemsPerPage.orElse(3));
                }
              result = consultationsServiceImplementation.getConsultationsByNumberPage(page.get(), itemsPerPage.orElse(3));

            }

            if (idPatient.isPresent()) {
                CollectionModel<EntityModel<ConsultationsCollection>> filteredResult = consultationsServiceImplementation.getConsultationsByIdPatient(idPatient.get());
                result = consultationsServiceImplementation.intersect(result, filteredResult);
                log.info("idPatient.isPresent() => result {}", result.getContent() );
            }
            if (idDoctor.isPresent()) {
                CollectionModel<EntityModel<ConsultationsCollection>> filteredResult = consultationsServiceImplementation.getConsultationsByIdDoctor(idDoctor.get());
                result = consultationsServiceImplementation.intersect(result, filteredResult);
                log.info("idDoctor.isPresent() => result {}",result.getContent() );

            }
            if (appointmentTime.isPresent()) {
                CollectionModel<EntityModel<ConsultationsCollection>> filteredResult = consultationsServiceImplementation.getConsultationsByAppointmentPK(appointmentTime.get());
                result = consultationsServiceImplementation.intersect(result, filteredResult);
            }
            if (diagnostic.isPresent()) {
                CollectionModel<EntityModel<ConsultationsCollection>> filteredResult = consultationsServiceImplementation.getConsultationsByDiagnostic(diagnostic.get());
                result = consultationsServiceImplementation.intersect(result, filteredResult);
            }
            log.info("RESPONSE: {}", result);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (BadRequestException badRequestException) { return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotFoundException notFoundException) { return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND); }
    }


    @Operation(summary = "Get consultation resource by _id field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConsultationsCollection.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/{_id}")
    public ResponseEntity<?> getConsultationById(@PathVariable String _id) {
        log.info("[{}] -> GET, getConsultationById: _id:{}", this.getClass().getSimpleName(), _id);
        Optional<ConsultationsCollection> consultation = Optional.ofNullable(consultationsRepository.findConsultationsCollectionBy_id(_id));

        if (consultation.isPresent()) {
            return new ResponseEntity<>(new ConsultationsHateoasSelfParent().toModel(consultation.get()), HttpStatus.OK);

        } else {
            Map<String, ArrayList<Link>> links = new HashMap<>();
            ArrayList<Link> arrayLinks = new ArrayList<Link>();
            Link parentLink = linkTo(methodOn(ConsultationsController.class).getAllConsultations(Optional.empty(), Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                    .withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<Link>(arrayLinks));

            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create consultation resource  with POST because _id is autogenerated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConsultationsCollection.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict - Resource already exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid content",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Requested representation not acceptable",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),

    })
    @PostMapping ()
    public ResponseEntity<?> createConsultation(
            @RequestBody ConsultationsCollection consultation
    ) {
        log.info("[{}] -> GET, createConsultation: idPatient:{}, idDoctor:{}, diagnostic:{}, appointmentTime:{}, investigations:{}",
                this.getClass().getSimpleName(), consultation.getIdPatient(), consultation.getIdDoctor(), consultation.getDiagnostic(), consultation.getAppointmentTime(), consultation.getInvestigations());
        try {
            /*
             * Due to the inability to generate a unique verification ID in the path, a quick check is performed in the controller on another unique field,
             * specifically the appointment specific fields:
             * - If there is no existing consultation with the same apppk, a new consultation is created.
             * - If there is a consultation with the same apppk, a conflict occurs as it violates the uniqueness constraint for the creation path.
             */
            ConsultationsCollection duplicateConsultation = consultationsRepository.findConsultationsCollectionByIdDoctorAndIdPatientAndAppointmentTime(consultation.getIdDoctor(),consultation.getIdPatient(),consultation.getAppointmentTime());
            if(  null != duplicateConsultation  )
            {
                log.info("exista deja o consultate pentu programarea din body");
                throw new ConflictException(CONSULTATION_WITH_THIS_APPOINTMENT_ALREADY_EXIST);

            }
            log.info("Trying to create consultation ... ");
            EntityModel<ConsultationsCollection> newCreatedConsultation = consultationsServiceImplementation.addConsultation(consultation);
            return new ResponseEntity(newCreatedConsultation, HttpStatus.CREATED);

        }
        catch (DeserealizationException deserealizationException) {return new ResponseEntity<>(deserealizationException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);}
        catch (ConflictException conflictException) {return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT);}
        catch (UnprocessableContentException unprocessableContentException) {return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);}
        catch (NotAcceptableException notAcceptableException) {return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) {return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT);}

    }

    @Operation(summary = "Create investigation resource  with POST because _id is autogenerated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConsultationsCollection.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict - Resource already exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid content",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Requested representation not acceptable",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),

    })
    @PostMapping ("/consultation/{_id}/investigation")
    public ResponseEntity<?> createInvestigation(
            @PathVariable final String _id,
            @RequestBody ConsultationsCollection consultation
    ) {
        log.info("[{}] -> GET, createInvestigation: idPatient:{}, idDoctor:{}, diagnostic:{}, appointmentTime:{}, investigations:{}", this.getClass().getSimpleName(), consultation.getIdPatient(), consultation.getIdDoctor(), consultation.getDiagnostic(), consultation.getAppointmentTime(), consultation.getInvestigations());
        try {

            /* For investigation  try first to exist consultation */
            if (!consultation.get_id().equals(_id)) {
                log.info("Path or field id wrong ... ");
                return new ResponseEntity(CONFLIC_MESSAGE_NOT_SAME_IDOBJECT, HttpStatus.CONFLICT);
            }
            EntityModel<ConsultationsCollection> newCreatedConsultation = consultationsServiceImplementation.addInvestigation(consultation);
            return new ResponseEntity(newCreatedConsultation, HttpStatus.CREATED);

        }
        catch (DeserealizationException deserealizationException) {return new ResponseEntity<>(deserealizationException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);}
        catch (ConflictException conflictException) {return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT);}
        catch (UnprocessableContentException unprocessableContentException) {return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);}
        catch (NotAcceptableException notAcceptableException) {return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) {return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT);}

    }

    @Operation(summary = "Update consultation resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resource updated successfully",  content = @Content ),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Invalid request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict - Requested representation conflicts with current state",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid content",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
    })
    @PatchMapping ("/{objectId}")
    public ResponseEntity<?> updateConsultation(
            @PathVariable final String objectId,
            @RequestBody ConsultationsCollection consultation
    ) {
       log.info("[{}] -> GET, updateConsultation: idPatient:{}, idDoctor:{}, diagnostic:{}, appointmentTime:{}, investigations:{}",
               this.getClass().getSimpleName(), consultation.getIdPatient(), consultation.getIdDoctor(), consultation.getDiagnostic(), consultation.getAppointmentTime(), consultation.getInvestigations());
        try {
            log.info("Trying to update consultation ... ");
            if(null == consultation.get_id())
            {
                throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
            }
            if (!consultation.get_id().equals(objectId)) {
                return new ResponseEntity(CONFLIC_MESSAGE_NOT_SAME_IDOBJECT, HttpStatus.CONFLICT);
            }
            EntityModel<ConsultationsCollection> newUpdatedDConsultation = consultationsServiceImplementation.updateConsultation(consultation, objectId);    //validarile si verificarile de existenta sunt prezente in serviciu : validare campuri, existenta altui user cu email identic etc
            return new ResponseEntity(newUpdatedDConsultation, HttpStatus.NO_CONTENT); //la update nu e necesar sa intorc ceva corp


        }
        catch (DeserealizationException deserealizationException) {
            return new ResponseEntity<>(deserealizationException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (ConflictException conflictException) { return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT); }
    }

    @Operation(summary = "Delete consultation resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource deleted successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConsultationsCollection.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Invalid request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @DeleteMapping("/{_id}")
    public ResponseEntity<?> deleteConsultation(@PathVariable String _id) {
        log.info("[{}] -> DELETE, deleteConsultation: _id:{},",
                this.getClass().getSimpleName(), _id);
        try {
            EntityModel entityModel = consultationsServiceImplementation.deleteConsultation(_id);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } catch (NotAcceptableException notAcceptableException) {
            return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (ConflictException conflictException) {
            return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
