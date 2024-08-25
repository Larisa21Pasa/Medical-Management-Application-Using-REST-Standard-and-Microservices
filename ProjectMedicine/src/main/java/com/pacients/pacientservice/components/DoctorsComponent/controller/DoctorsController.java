package com.pacients.pacientservice.components.DoctorsComponent.controller;

import com.pacients.pacientservice.components.DoctorsComponent.hateoas.SelfParent.DoctorsHateoasSelfParent;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.DoctorsComponent.model.repository.DoctorsRepository;
import com.pacients.pacientservice.components.DoctorsComponent.service.DoctorsServiceImplementation;
import com.pacients.pacientservice.utils.Exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Log4j2
@RestController
@RequestMapping("/api/medical_office/doctors")
public class DoctorsController {
    @Autowired
    private DoctorsRepository doctorsRepository;

    @Autowired
    private DoctorsServiceImplementation doctorsServiceImplementation;

    /*--------- GET ----------*/
     /*
       NAME METHOD: getAllDoctors from class DoctorsController
       SCOPE: Retrieve doctor information with optional filters and return a ResponseEntity.

       HTTP CODES:
         *** 200 -> OK
             - Successful retrieval of doctor information returns a 200 response.

         *** 400 -> Bad Request
             - Returned if there is a bad request, such as invalid parameters - > just to catch something that I missed.

         *** 422 -> Unprocessable Entity
             - Returned if there are issues with processing the request.

        *** 406 -> Not acceptable Entity
             - Returned if there are issues with element from body.

         *** 404 -> Not Found
             - Returned if no doctors are found based on the provided filters -> not in current case, but for eventually modification of this function.

       NOTE:
           Retrieve doctor information with optional filters using the HTTP GET method.
           If any optional filters are provided, apply them using the respective service methods and intersect the results.
           Return a ResponseEntity with the appropriate HTTP status code and result.
    */
    @Operation(summary = "Get all/searched doctors resources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - Found all/searched resources",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DoctorsTable.class)))}),
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
    public ResponseEntity<?> getAllDoctors(
            @RequestParam(value = "page")  @Min(0) Optional<Integer> page,
            @RequestParam(value="itemsPerPage", defaultValue = "3" )  @Min(value = 1, message = "Page should be at least 1.")Optional<Integer> itemsPerPage,
            @RequestParam(value = "lastname") Optional<String> lastname,
            @RequestParam(value = "firstname") Optional<String> firstname,
            @RequestParam(value = "email") Optional<String> email,
            @RequestParam(value = "phone") Optional<String> phone,
            @RequestParam(value = "specialization") Optional<String> specialization
    ) {
        log.info("[{}] -> GET, getAllDoctors: lastname:{}, firstname:{}, email:{}, phone:{}, specialization:{}",
                this.getClass().getSimpleName(), lastname, firstname, email, phone, specialization);
        try {
            CollectionModel<EntityModel<DoctorsTable>> result = doctorsServiceImplementation.getAllDoctors();

            if(page.isPresent())
            {
                if (page.get() < 0)
                {
                    doctorsServiceImplementation.provideHeateoasForPageable(itemsPerPage.orElse(3));
                }
                 result = doctorsServiceImplementation.getDoctorsByNumberPage(page.get(), itemsPerPage.orElse(3));

            }

            if (lastname.isPresent()) {
                log.info("lastname.isPresent()");
                CollectionModel<EntityModel<DoctorsTable>> filteredResult = doctorsServiceImplementation.getDoctorByLastame(lastname.get());
                log.info("specialization.isPresent() => filteredResult {} ", filteredResult);

                result = doctorsServiceImplementation.intersectFilteredDoctors(result, filteredResult);
                log.info("specialization.isPresent() => result {} ", result);

            }

            if (firstname.isPresent()) {
                CollectionModel<EntityModel<DoctorsTable>> filteredResult = doctorsServiceImplementation.getDoctorByFirstame(firstname.get());
                result = doctorsServiceImplementation.intersectFilteredDoctors(result, filteredResult);
            }

            if (email.isPresent()) {
                CollectionModel<EntityModel<DoctorsTable>> filteredResult = doctorsServiceImplementation.getDoctorByEmail(email.get());
                result = doctorsServiceImplementation.intersectFilteredDoctors(result, filteredResult);
            }

            if (phone.isPresent()) {
                CollectionModel<EntityModel<DoctorsTable>> filteredResult = doctorsServiceImplementation.getDoctorByPhone(phone.get());
                result = doctorsServiceImplementation.intersectFilteredDoctors(result, filteredResult);
            }

            if (specialization.isPresent()) {
                log.info("specialization.isPresent()");
                CollectionModel<EntityModel<DoctorsTable>> filteredResult = doctorsServiceImplementation.getDoctorBySpecialization(specialization.get());
                log.info("specialization.isPresent() => filteredResult {} ", filteredResult);
                result = doctorsServiceImplementation.intersectFilteredDoctors(result, filteredResult);
                log.info("specialization.isPresent() => result {} ", result);

            }

            /* Because in this case it is returned a container, even if is empty container
            -> it will be returned 200 ok with empty resource
            */
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (BadRequestException badRequestException) { return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotFoundException notFoundException) { return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND); }

    }



    /*
       NAME METHOD: getDoctorByIdUser from class DoctorsController
       SCOPE: Retrieve doctor information by user ID and return a ResponseEntity.
       PATH: "/user/{idUser}"
       HTTP CODES:
         *** 200 -> OK
             - Successful retrieval of doctor information returns a 200 response.

         *** 404 -> Not Found
             - Returned if no doctor is found for the provided user ID.

       NOTE:
           Retrieve resource information by user ID using the HTTP GET method.
           If a resource is found for the provided user ID, return the resource information with HATEOAS links.
           Otherwise, return links to the parent( all resource ) resource with a 404 Not Found status.
    */
    @Operation(summary = "Get doctor resource by idUser field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DoctorsTable.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> getDoctorByIdUser(@PathVariable Integer idUser) {
        Optional<DoctorsTable> doctor = Optional.ofNullable(doctorsRepository.findDoctorsTableByIdUser(idUser));
        if(doctor.isPresent())
        {
            return new ResponseEntity<>(new DoctorsHateoasSelfParent().toModel(doctor.get()), HttpStatus.OK);

        }
        else {
            Map<String, ArrayList<Link>> links = new HashMap<>();
            ArrayList<Link> arrayLinks = new ArrayList<Link>();
            Link parentLink = linkTo(methodOn(com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController.class)
                    .getAllDoctors(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()))
                    .withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<Link>(arrayLinks));

            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }
    }

    /*
      NAME METHOD: getDoctorByIdDoctor from class DoctorsController
      REST OF EXPLANATIONS: same as getDoctorByIdUser
   */
    @Operation(summary = "Get doctor resource by own ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DoctorsTable.class))}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundException.class))})
    })
    @GetMapping("/{idDoctor}")
    public ResponseEntity<?> getDoctorByIdDoctor(@PathVariable Integer idDoctor) {
        log.info("[{}] -> GET, getDoctorByIdDoctor: idDoctor:{}", this.getClass().getSimpleName(), idDoctor);

        EntityModel<DoctorsTable> doctor = doctorsServiceImplementation.getDoctorByIdDoctor(idDoctor);
        if (null != doctor) {
            return new ResponseEntity<>(doctor, HttpStatus.OK);

        } else {
            Map<String, ArrayList<Link>> links = new HashMap<>();
            ArrayList<Link> arrayLinks = new ArrayList<Link>();
            Link parentLink = linkTo(methodOn(DoctorsController.class).getAllDoctors(Optional.empty(),Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                    .withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<Link>(arrayLinks));

            return new ResponseEntity<>(links, HttpStatus.NOT_FOUND);
        }
    }



    /*--------- POST ----------*/


    /*
       NAME METHOD: createDoctor from class DoctorsController
       SCOPE: Create a new doctor resource using the HTTP POST method and return a ResponseEntity.
       PATH: "/"
       HTTP CODES:
         *** 201 -> Created
             - Successful creation of a new doctor resource returns a 201 response.

         *** 204 -> No Content
             - Successful replacement of an existing resource with a given ID returns a 204 response.

         *** 400 -> Bad Request
             - Incorrect syntax for path variables returns a 400 response.

         *** 406 -> Not Acceptable
                 - Field missing from the body returns a 406 response.

             *** 409 -> Conflict
                 - Conflict occurs if a doctor with the same email already exists, violating the unique email constraint.
                 - Returns a 409 response with a ConflictException.

             *** 422 -> Unprocessable Entity
                 - Invalid values for request fields return a 422 response.
                 - Returns an UnprocessableContentException.

           NOTE:
             The update operation is performed using HTTP POST because we do not set the ID ourselves, and control is semi-total.
             This choice is made to align with the RESTful convention where PUT is typically used for full updates with a client-specified ID.

    */
    @Operation(summary = "Create doctor resource  with POST because ID is autogenerated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DoctorsTable.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict - Resource already exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConflictException.class))}),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid content",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UnprocessableContentException.class))}),
            @ApiResponse(responseCode = "406", description = "Not Acceptable - Requested representation not acceptable",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NotAcceptableException.class))}),

    })
    @PostMapping ()
    public ResponseEntity<?> createDoctor(
            @RequestBody DoctorsTable doctor
    ) {
        log.info("[{}] -> POST, createDoctor: lastname:{}, firstname:{}, email:{}, phone:{}, specialization:{}",
                this.getClass().getSimpleName(), doctor.getLastname(), doctor.getFirstname(), doctor.getEmail(), doctor.getPhone(), doctor.getSpecialization());
        try {
            /*
             * Due to the inability to generate a unique verification ID in the path, a quick check is performed in the controller on another unique field,
             * specifically the 'email' field:
             * - If there is no existing doctor with the same email, a new doctor is created.
             * - If there is a doctor with the same email, a conflict occurs as it violates the uniqueness constraint for the creation path.
             */
            DoctorsTable duplicateDoctor = doctorsRepository.findDoctorsTableByEmail(doctor.getEmail());
            if(  null != duplicateDoctor  )
            {
                throw new ConflictException(RESOURCE_ALREADY_EXIST);
            }

            log.info("Trying to create doctor ... ");
            EntityModel<DoctorsTable> newCreatedDoctor = doctorsServiceImplementation.addDoctor(doctor);
            return new ResponseEntity(newCreatedDoctor, HttpStatus.CREATED);

        }
        catch (DeserealizationException deserealizationException) {return new ResponseEntity<>(deserealizationException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);}
        catch (ConflictException conflictException) { return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT);}
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT); }
    }

    /*
       NAME METHOD: updateDoctor from class DoctorsController
       SCOPE: Update an existing doctor resource using the HTTP PUT method and return a ResponseEntity.
       PATH: "/{idDoctor}"
       HTTP CODES:
         *** 204 -> No Content
             - Successfully replaced/updated an existing resource with the given ID returns a 204 response.

         *** 400 -> Bad Request
             - Incorrect syntax for path variables returns a 400 response.

         *** 404 -> Not Found
             - Doctor not found for the given ID returns a 404 response.

             *** 406 -> Not Acceptable
                 - Field missing from the body returns a 406 response.

             *** 422 -> Unprocessable Entity
                 - Invalid values for request fields return a 422 response.
                 - Returns an UnprocessableContentException.
    */

    @Operation(summary = "Update doctor resource.")
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
    @PutMapping ("/{idDoctor}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable final Integer idDoctor,
            @RequestBody DoctorsTable doctor
    ) {
        log.info("[{}] -> POST, updateDoctor:idDoctor{}, lastname:{}, firstname:{}, email:{}, phone:{}, specialization:{}",
                this.getClass().getSimpleName(),doctor.getIdDoctor(), doctor.getLastname(), doctor.getFirstname(), doctor.getEmail(), doctor.getPhone(), doctor.getSpecialization());
        try {

            if(null == doctor.getIdDoctor())
            {
                throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
            }

            if (!doctor.getIdDoctor().equals(idDoctor)) {
                return new ResponseEntity(CONFLIC_EXCEPTION_NOT_SAME_ID, HttpStatus.CONFLICT);
            }
            EntityModel<DoctorsTable> newUpdatedDoctor = doctorsServiceImplementation.updateDoctor(doctor, idDoctor);
            return new ResponseEntity(newUpdatedDoctor, HttpStatus.NO_CONTENT);


        }
        catch (ConflictException conflictException) { return new ResponseEntity<>(conflictException.getMessage(), HttpStatus.CONFLICT); }
        catch (UnprocessableContentException unprocessableContentException) { return new ResponseEntity<>(unprocessableContentException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); }
        catch (NotAcceptableException notAcceptableException) { return new ResponseEntity<>(notAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE); }
        catch (RuntimeException runtimeException) { return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT); }
    }


}
