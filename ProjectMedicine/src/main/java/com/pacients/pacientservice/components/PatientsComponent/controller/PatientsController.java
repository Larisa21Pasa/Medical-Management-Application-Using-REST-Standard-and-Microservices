package com.pacients.pacientservice.components.PatientsComponent.controller;
import com.pacients.pacientservice.components.PatientsComponent.hateoas.SelfAndParent.PatientsHateoasSelfParent;
import com.pacients.pacientservice.components.PatientsComponent.service.PatientsServiceImplementation;
import com.pacients.pacientservice.utils.Exceptions.*;
import com.pacients.pacientservice.components.PatientsComponent.model.repository.PatientsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.CONFLIC_EXCEPTION_NOT_SAME_ID;
import static com.pacients.pacientservice.utils.Logs.ProgramLogs.NOT_FOUND_EXCEPTION;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/api/medical_office/patients")
public class PatientsController {

    @Autowired
    private PatientsRepository pacientRepo;

    @Autowired
    private PatientsServiceImplementation patientsServiceImplementation;



    /*--------- GET ----------*/
     /*
       NAME METHOD: getAllPatients from class PatientsController
       SCOPE: Retrieve patient information with optional filters and return a ResponseEntity.

       HTTP CODES:
         *** 200 -> OK
             - Successful retrieval of patient information returns a 200 response.

         *** 400 -> Bad Request
             - Returned if there is a bad request, such as invalid parameters - > just to catch something that I missed.

         *** 422 -> Unprocessable Entity
             - Returned if there are issues with processing the request.

        *** 406 -> Not acceptable Entity
             - Returned if there are issues with element from body.

         *** 404 -> Not Found
             - Returned if no patients are found based on the provided filters -> not in current case, but for eventually modification of this function.

       NOTE:
           Retrieve patient information with optional filters using the HTTP GET method.
           If any optional filters are provided, apply them using the respective service methods and intersect the results.
           Return a ResponseEntity with the appropriate HTTP status code and result.
    */
    @Operation(summary = "Get all/searched patient resources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - Found all/searched resources",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientsTable.class)))}),
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
    public ResponseEntity<?> getAllPatients(
            @RequestParam(value = "lastname") Optional<String> lastname,
            @RequestParam(value = "firstname") Optional<String> firstname,
            @RequestParam(value = "email") Optional<String> email,
            @RequestParam(value = "phone") Optional<String> phone,
            @RequestParam(value = "birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> birthdate
    ) {
        log.info("[{}] -> GET, getAllPatients: lastname:{}, firstname:{}, email:{}, phone:{}, birthdate:{}",
                this.getClass().getSimpleName(), lastname, firstname, email, phone, birthdate);
        try {
            CollectionModel<EntityModel<PatientsTable>> result = patientsServiceImplementation.getAllPatients();

            if (lastname.isPresent()) {
                CollectionModel<EntityModel<PatientsTable>> filteredResult = patientsServiceImplementation.getPatientByLasttame(lastname.get());
                result = patientsServiceImplementation.intersect(result, filteredResult);
                log.info("lastname.isPresent() => result {}", result.getContent() );

            }

            if (firstname.isPresent()) {
                CollectionModel<EntityModel<PatientsTable>> filteredResult = patientsServiceImplementation.getPatientByFirstame(firstname.get());
                result = patientsServiceImplementation.intersect(result, filteredResult);
                log.info("firstname.isPresent() => result {}", result.getContent() );

            }

            if (email.isPresent()) {
                CollectionModel<EntityModel<PatientsTable>> filteredResult = patientsServiceImplementation.getPatientByEmail(email.get());
                result = patientsServiceImplementation.intersect(result, filteredResult);
            }

            if (phone.isPresent()) {
                CollectionModel<EntityModel<PatientsTable>> filteredResult = patientsServiceImplementation.getPatientByPhone(phone.get());
                result = patientsServiceImplementation.intersect(result, filteredResult);
            }

            if (birthdate.isPresent()) {
                CollectionModel<EntityModel<PatientsTable>> filteredResult = patientsServiceImplementation.getPatientByBirthdate(birthdate.get());
                result = patientsServiceImplementation.intersect(result, filteredResult);
            }
            /* Because in this case it is returned a container, even if is empty container
            -> it will be returned 200 ok with empty resource
            */

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (BadRequestException badRequestException) { log.info("BadRequestException"); return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST); }
        catch (NotAcceptableException notAcceptableException) { log.info("NotAcceptableException"); return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (UnprocessableContentException unprocessableContentException) { log.info("UnprocessableContentException"); return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotFoundException notFoundException) { log.info("NotFoundException"); return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND); }
        catch (RuntimeException runtimeException) {log.info("RuntimeException"); return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND); }

    }


    /*
       NAME METHOD: getPatientByIdUser from class PatientsController
       SCOPE: Retrieve patient information by user ID and return a ResponseEntity.
       PATH: "/user/{idUser}"
       HTTP CODES:
         *** 200 -> OK
             - Successful retrieval of patient information returns a 200 response.

         *** 404 -> Not Found
             - Returned if no patient is found for the provided user ID.

       NOTE:
           Retrieve patient information by user ID using the HTTP GET method.
           If a patient is found for the provided user ID, return the patient information with HATEOAS links.
           Otherwise, return links to the parent( all patients ) resource with a 404 Not Found status.
    */
    @Operation(summary = "Get patient resource by idUser field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientsTable.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> getPatientByIdUser(@PathVariable Integer idUser) {
        log.info("[{}] -> GET, getPacientByIdUser: idUser:{}", this.getClass().getSimpleName(), idUser);
        Optional<PatientsTable> patient = Optional.ofNullable(pacientRepo.findPatientsTableByIdUser(idUser));
        if (patient.isPresent()) {
            return new ResponseEntity<>(new PatientsHateoasSelfParent().toModel(patient.get()), HttpStatus.OK);

        } else {
            Map<String, ArrayList<Link>> links = new HashMap<>();
            ArrayList<Link> arrayLinks = new ArrayList<Link>();
            Link parentLink = linkTo(methodOn(PatientsController.class).getAllPatients(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty())
            ).withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<Link>(arrayLinks));

            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }
    }

    /*
      NAME METHOD: getPatientByCnp from class PatientsController
      REST OF EXPLANATIONS: same as getPatientByIdUser
   */
    @Operation(summary = "Get patient resource by own ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientsTable.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/{cnp}")
    public ResponseEntity<?> getPatientByCnp(@PathVariable String cnp) {
        log.info("[{}] -> GET, getPacientByCnp: cnp:{}", this.getClass().getSimpleName(), cnp);

        EntityModel<PatientsTable> patient = patientsServiceImplementation.getPatientByCnp(cnp);
        if (null != patient) {
            return new ResponseEntity<>(patient, HttpStatus.OK);

        } else {
            Map<String, ArrayList<Link>> links = new HashMap<>();
            ArrayList<Link> arrayLinks = new ArrayList<Link>();
            Link parentLink = linkTo(methodOn(PatientsController.class).getAllPatients(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())).withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<Link>(arrayLinks));

            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }
    }



    /*--------- PUT ----------*/
    /*
       NAME METHOD: createOrUpdatePatient from class PatientsController
       SCOPE: Create or update patient information using the HTTP PUT method and return a ResponseEntity.
       PATH: "/{cnp}"
       HTTP CODES:
         *** 201 -> Created
             - Successful creation of a new patient resource returns a 201 response.

         *** 204 -> No Content
             - Successful update of an existing patient resource returns a 204 response.

         *** 409 -> Conflict
             - Returned if there is a conflict, such as attempting to create or update a patient with a different CNP.

         *** 422 -> Unprocessable Entity
             - Returned if there are issues with processing the request, such as invalid input data.

       NOTE:
       Create or update patient information using the HTTP PUT method.
       If the patient with the provided CNP is not found in the database, create a new patient resource.
       If the patient with the provided CNP is found in the database, update the existing patient resource.
       In this case, the CNP is used as the identifier for the patient in the database,
       and therefore, the HTTP PUT method is used for both creation and updating.
    */
    @Operation(summary = "Create/update patient resource with PUT because ID is manually.")
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
    @PutMapping("/{cnp}")
    public ResponseEntity<?> createORUpdatePatient(
            @PathVariable final String cnp,
            @RequestBody PatientsTable patient
    ) {
        log.info("[{}] -> PUT, createORUpdatePatient: cnp:{}, iduser:{}, lastname:{}, firstname:{}, email:{}, phone:{}, birthdate:{}, isactive:{}",
                this.getClass().getSimpleName(), patient.getCnp(), patient.getIdUser(), patient.getLastname(), patient.getFirstname(), patient.getEmail(), patient.getPhone(), patient.getBirthdate(), patient.getIsactive());
        try {
            if (null == pacientRepo.findPatientsTableByCnp(patient.getCnp())) {
                /* Create Patient resource if NOT find id in database*/
                if (!cnp.equals(patient.getCnp())) {
                    return new ResponseEntity(CONFLIC_EXCEPTION_NOT_SAME_ID, HttpStatus.CONFLICT);
                }
                log.info("Patient unique, creating ... ");
                EntityModel<PatientsTable> newCreatedPatient = patientsServiceImplementation.addPatient(patient);
                return new ResponseEntity(newCreatedPatient, HttpStatus.CREATED);
            }
            else {
                /* Update Patient resource if find id in database*/
                log.info("Trying to update patient ... ");
                if (!patient.getCnp().equals(cnp)) {
                    return new ResponseEntity(CONFLIC_EXCEPTION_NOT_SAME_ID, HttpStatus.CONFLICT);
                }
                log.info("Patient existing, updating ... ");
                EntityModel<PatientsTable> newUpdatedPatient = patientsServiceImplementation.updatePatient(patient, cnp);
                return new ResponseEntity(newUpdatedPatient, HttpStatus.NO_CONTENT);
            }
        }
        catch (ConflictException conflictException) { return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT); }
    }

    /*--------- DELETE ----------*/
    /*
       NAME METHOD: deletePatient from class PatientsController
       SCOPE: Delete patient information using the HTTP DELETE method and return a ResponseEntity.
       PATH: "/{cnp}"
       HTTP CODES:
         *** 200 -> OK
             - Successful deletion of the patient resource returns a 200 response.

         *** 406 -> Not Acceptable
             - Returned if there are issues with processing the request, such as the patient being associated with active records.

         *** 404 -> Not Found
             - Returned if no patient is found for the provided CNP.
       NOTE:
           Delete patient information using the HTTP DELETE method.

    */
    @Operation(summary = "Delete patient $›resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource deleted successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PatientsTable.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Invalid request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @DeleteMapping("/{cnp}")
    public ResponseEntity<?> deletePatient(@PathVariable String cnp) {
        log.info("[{}] -> DELETE, deletePacient: cnp:{},", this.getClass().getSimpleName(), cnp);
        try {
            EntityModel entityModel = patientsServiceImplementation.deletePatient(cnp);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } catch (NotAcceptableException notAcceptableException) {
            return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (ConflictException conflictException) {
            return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}