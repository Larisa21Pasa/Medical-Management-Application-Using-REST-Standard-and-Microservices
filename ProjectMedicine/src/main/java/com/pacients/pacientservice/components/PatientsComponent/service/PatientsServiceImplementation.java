package com.pacients.pacientservice.components.PatientsComponent.service;

import com.pacients.pacientservice.components.PatientsComponent.controller.PatientsController;
import com.pacients.pacientservice.components.PatientsComponent.hateoas.Custom.PatientsHateoasSelfParentCustom;
import com.pacients.pacientservice.components.PatientsComponent.hateoas.Parent.PatientsHateoasParent;
import com.pacients.pacientservice.components.PatientsComponent.hateoas.SelfAndParent.PatientsHateoasSelfParent;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import com.pacients.pacientservice.components.PatientsComponent.model.repository.PatientsRepository;
import com.pacients.pacientservice.utils.Exceptions.*;
import com.pacients.pacientservice.utils.Validators.BirthData.BirthDateValidator;
import com.pacients.pacientservice.utils.Validators.Cnp.CnpValidator;
import com.pacients.pacientservice.utils.Validators.Email.EmailValidator;
import com.pacients.pacientservice.utils.Validators.Name.NameValidator;
import com.pacients.pacientservice.utils.Validators.Phone.PhoneValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Log4j2
@Service
public class PatientsServiceImplementation implements PatientsService {
    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private PatientsHateoasSelfParent patientsHateoasSelfParent;
    @Autowired
    private PatientsHateoasParent patientsHateoasParent;
    @Autowired
    private PatientsHateoasSelfParentCustom patientsHateoasSelfParentCustom;

    private CnpValidator cnpValidator = new CnpValidator();
    private NameValidator nameValidator = new NameValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private PhoneValidator phoneValidator = new PhoneValidator();
    private BirthDateValidator birthDateValidator = new BirthDateValidator();


    /*--------- GET ----------*/
    /*
    NAME METHOD: getAllPatients from class PatientsServiceImplementation
    SCOPE: ...
    HTTP CODES:

     */
    /*
       NAME METHOD: getAllPatients from class PatientsServiceImplementation
       SCOPE: Retrieve all patient information with HATEOAS links.
    */
    @Override
    public CollectionModel<EntityModel<PatientsTable>> getAllPatients() {
        log.info("getAllPatients() from PatientsServiceImplementation");
        /* Patient resource + SelfParent Hateoas */
        List<EntityModel<PatientsTable>> listPatients = new ArrayList<>();

        /* Find Patient resource and associate each data with hateoas component */
        List<PatientsTable> patients = patientsRepository.findAll();
        for (PatientsTable patient : patients) {
            EntityModel<PatientsTable> entityModel = patientsHateoasSelfParent.toModel(patient);
            listPatients.add(entityModel);
        }
        return CollectionModel.of(listPatients);
    }



    /*
       NAME METHOD: getPatientByCnp from class PatientsServiceImplementation
       SCOPE: Retrieve patient information by CNP with HATEOAS links.

       HTTP CODES:
         *** 422 -> Unprocessable Entity
             - Invalid CNP syntax results in a 422 response.

         *** 404 -> Not Found
             - No patient found for the given CNP triggers a 404 response.
    */
    @Override
    public EntityModel<PatientsTable> getPatientByCnp(String cnp) {
        log.info("getPatientByCnp() from PatientsServiceImplementation");

        /* Check if query is valid firstly  */
        if(!cnpValidator.isValid(cnp, null))
        {
            throw new UnprocessableContentException(ID_RESOURCE_INVALID);
        }

        /* Patient resource + SelfParent Hateoas */
        PatientsTable patient = patientsRepository.findPatientsTableByCnp(cnp);
        if (null == patient)
        {
            /*Because it's a single resource, the method should return a PatientsTable object.
            In this specific case, if the method returns null, it is appropriate to respond with a 404 status code.*/
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }
        else
        {
            return patientsHateoasSelfParent.toModel(patient);
        }
    }


    /*
       NAME METHOD: getPatientByFirstname from class PatientsServiceImplementation
       SCOPE: Retrieve a collection of patient information by firstname with HATEOAS links.

       HTTP CODES:
         *** 422 -> Unprocessable Entity
             - Invalid firstname syntax results in a 422 response.

       NOTE:
       When no matching patients are found, an empty collection is returned.
       This design choice is made as an empty collection is still a valid object .
    */
    public CollectionModel<EntityModel<PatientsTable>> getPatientByFirstame(String firstname){
        log.info("getPatientByFirstame() from PatientsServiceImplementation");

        if(!nameValidator.isValid(firstname, null))
        {
            throw new UnprocessableContentException("stirile protv"+UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }
        List<PatientsTable> patients = patientsRepository.findPatientsTableByFirstnameContaining(firstname);
        List<EntityModel<PatientsTable>> listPatients = patients.stream()
                .map(patientsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        PatientsTable patientForCustomHateoas = new PatientsTable();
        patientForCustomHateoas.setFirstname(firstname);

        return  getBodyWithHateoas(listPatients, patientForCustomHateoas);
    }


    /*
        NAME METHOD: getPatientByLasttame from class PatientsServiceImplementation
        REST OF EXPLANATIONS: same as getPatientByFirstame
     */
    public CollectionModel<EntityModel<PatientsTable>> getPatientByLasttame(String lastname) {
        log.info("getPatientByLasttame() from PatientsServiceImplementation");

        if(!nameValidator.isValid(lastname, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }
        List<PatientsTable> patients = patientsRepository.findPatientsTableByLastnameContaining(lastname);

        List<EntityModel<PatientsTable>> listPatients = patients.stream()
                .map(patientsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        PatientsTable patientForCustomHateoas = new PatientsTable();
        patientForCustomHateoas.setLastname(lastname);

        return  getBodyWithHateoas(listPatients, patientForCustomHateoas);
    }

    /*
       NAME METHOD: getPatientByEmail from class PatientsServiceImplementation
       REST OF EXPLANATIONS: same as getPatientByFirstame
    */
    @Override
    public CollectionModel<EntityModel<PatientsTable>> getPatientByEmail(String email) {
        log.info("getPatientByEmail() from PatientsServiceImplementation");

        /*
            Despite using 'containing' in the repository function, the
            validation checks the entire email string against the regex for a complete email match
        */
        if(!emailValidator.isValid(email, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<PatientsTable> patients = patientsRepository.findPatientsTableByEmailContaining(email);
        List<EntityModel<PatientsTable>> listPatients = patients.stream()
                .map(patientsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        PatientsTable patientForCustomHateoas = new PatientsTable();
        patientForCustomHateoas.setEmail(email);

        return  getBodyWithHateoas(listPatients, patientForCustomHateoas);
    }


    /*
       NAME METHOD: getPatientByPhone from class PatientsServiceImplementation
       REST OF EXPLANATIONS: same as getPatientByFirstame
    */
    @Override
    public CollectionModel<EntityModel<PatientsTable>> getPatientByPhone(String phone) {
        log.info("getPatientByPhone() from PatientsServiceImplementation");

        /*
            Despite using 'containing' in the repository function, the
            validation checks the entire phone string against the regex for a complete phone match: ^07[2457]{1}[0-9]{7}$
        */
        if (!phoneValidator.isValid(phone, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        PatientsTable patient = patientsRepository.findPatientsTableByPhone(phone);
        EntityModel<PatientsTable> entityModel = patientsHateoasSelfParent.toModel(patient);

        PatientsTable patientForCustomHateoas = new PatientsTable();
        patientForCustomHateoas.setPhone(phone);

        // Create a list containing the single entityModel
        List<EntityModel<PatientsTable>> entityModelList = Collections.singletonList(entityModel);

        // Call getFilteredBody with the list
        return getBodyWithHateoas(entityModelList, patientForCustomHateoas);
    }

    /*
        NAME METHOD: getPatientByBirthdate from class PatientsServiceImplementation
        REST OF EXPLANATIONS: same as getPatientByFirstame
     */
    @Override
    public CollectionModel<EntityModel<PatientsTable>> getPatientByBirthdate(Date birthdate) {
        log.info("getPatientByBirthdate() from PatientsServiceImplementation");

        String formattedBirthdate = formatYYYYMMdd(birthdate);
        if (!birthDateValidator.isValid(formattedBirthdate, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }
        List<PatientsTable> patients = patientsRepository.findPatientsTableByBirthdate(birthdate);

        List<EntityModel<PatientsTable>> listPatients = patients.stream()
                .map(patientsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        PatientsTable patientForCustomHateoas = new PatientsTable();
        patientForCustomHateoas.setBirthdate(birthdate);

        return  getBodyWithHateoas(listPatients, patientForCustomHateoas);
    }


    /*--------- PUT ----------*/
    /*
       NAME METHOD: addPatient from class PatientsServiceImplementation
       SCOPE: Add a new patient with input validation and potential conflict checks.

       HTTP CODES:
         *** 406 -> Not Acceptable
             - Returned if there are missing or invalid input data.

         *** 422 -> Unprocessable Entity
             - Returned if input field validation fails.

         *** 409 -> Conflict
             - Returned if there is an attempt to add a patient with the same email but a different CNP.
    */
    @Override
    public EntityModel<PatientsTable> addPatient(PatientsTable patientsTable) {
        log.info("addPatient() from PatientsServiceImplementation");

        /*
           If there are missing or invalid input data, returning HTTP status code 406 (Not Acceptable)
           is appropriate, as it signifies that the server cannot process and respond to the client's
           request due to the absence or incorrectness of the provided data.
        */
        if(
                null == patientsTable.getCnp()
                ||null == patientsTable.getIdUser()
                || null == patientsTable.getLastname()
                || null == patientsTable.getFirstname()
                || null == patientsTable.getEmail()
                || null == patientsTable.getPhone()
                || null == patientsTable.getBirthdate()
                || null == patientsTable.getIsactive()
        )
        {
            log.info("addPatient -> NOT_ACCEPTABLE_EXCEPTION for {}", patientsTable);
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }
        /*
           Validate input fields using respective validators. If any of the validations fail,
           throw an UnprocessableContentException with a specific message indicating the fields
           that did not pass the validation.
        */
        log.info("CHECK FIELDS ");
        if(
                !cnpValidator.isValid(patientsTable.getCnp(), null)
                || !nameValidator.isValid(patientsTable.getLastname(), null)
                || !nameValidator.isValid(patientsTable.getFirstname(), null)
                || !emailValidator.isValid(patientsTable.getEmail(), null)
                || !phoneValidator.isValid(patientsTable.getPhone(), null)
                || !birthDateValidator.isValid(formatYYYYMMdd(patientsTable.getBirthdate()), null)
        )
        {
            log.info(" throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);\n");
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        log.info("dupa check");
        /*
           Check for an existing patient with the same email but a different CNP.
           If found, throw a ConflictException with HTTP status code 409.
        */
        PatientsTable duplicatePatient = patientsRepository.findPatientsTableByEmail(patientsTable.getEmail());
        if((  null != duplicatePatient  ) && !Objects.equals(duplicatePatient.getCnp(), patientsTable.getCnp()))
        {
            log.info("duplicat");
            throw new ConflictException(RESOURCE_ALREADY_EXIST);
        }

        try{
            patientsRepository.save(patientsTable);
        }catch ( DataAccessException dataAccessException)
        {
            throw  new RuntimeException(SAVE_ERROR + dataAccessException.getRootCause());
        }

        patientsTable = patientsRepository.findPatientsTableByCnp(patientsTable.getCnp());
        return patientsHateoasSelfParent.toModel(patientsTable);

    }

    /*
       NAME METHOD: updatePatient from class PatientsServiceImplementation
       SCOPE: Update an existing patient with input validation.

       HTTP CODES:
         *** 404 -> Not Found
             - Returned if the patient to be updated is not found.

         *** 422 -> Unprocessable Entity
             - Returned if there are missing or invalid input data.

         *** 406 -> Not Acceptable
             - Returned if there are missing or invalid input data during the  update.
    */
    @Override
    public EntityModel<PatientsTable> updatePatient(PatientsTable patientsTable, String cnp) {
        log.info("updatePatient() from PatientsServiceImplementation");

        /* Check resource to be existent */
//        if(null == patientsTable.getCnp())
//        {
//            throw new NotFoundException(NOT_FOUND_EXCEPTION); /* Or should throw 406 because of missing cnp from body? */
//        }
        if(null == patientsRepository.findPatientsTableByCnp(patientsTable.getCnp()))
        {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }

        /* Check ID (cnp) validity first .*/
        if(! cnpValidator.isValid(patientsTable.getCnp(), null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        /*
           Ensure all required fields are present in the entire resource during the PUT update.
           This is crucial as PUT replaces the entire resource. If any field is missing, throw
           a NotAcceptableException.
        */
        if(
                 null == patientsTable.getIdUser()
                || null == patientsTable.getLastname()
                || null == patientsTable.getFirstname()
                || null == patientsTable.getEmail()
                || null == patientsTable.getPhone()
                || null == patientsTable.getBirthdate()
                || null == patientsTable.getIsactive()
        )
        {
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }


        /*
       Validate input fields using respective validators. If any of the validations fail,
       throw an UnprocessableContentException with a specific message indicating the fields
       that did not pass the validation.
        */
        if(
                !cnpValidator.isValid(patientsTable.getCnp(), null)
                || !nameValidator.isValid(patientsTable.getLastname(), null)
                || !nameValidator.isValid(patientsTable.getFirstname(), null)
                || !emailValidator.isValid(patientsTable.getEmail(), null)
                || !phoneValidator.isValid(patientsTable.getPhone(), null)
                || !birthDateValidator.isValid(formatYYYYMMdd(patientsTable.getBirthdate()), null)
        )
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        try{
            log.info("not delete patient and create updated");
            patientsRepository.save(patientsTable);

        }catch (DataAccessException dataAccessException)
        {
            throw  new RuntimeException(UPDATE_ERROR + dataAccessException.getRootCause());
        }

        patientsTable = patientsRepository.findPatientsTableByCnp(patientsTable.getCnp());
        return patientsHateoasSelfParent.toModel(patientsTable);
    }

    /*--------- DELETE ----------*/
    /*
        NAME METHOD: deletePatient from class PatientsServiceImplementation
        SCOPE: Mark a patient as inactive, simulating a soft delete.

        HTTP CODES:
         *** 404 -> Not Found
             - Returned if the patient is not found.

        NOTE:
           Find the patient by CNP, mark it as inactive by setting "isactive" to false,
           and save the changes. This simulates a soft delete.
           Return the updated patient with HATEOAS links.
    */
    @Override
    public EntityModel<PatientsTable> deletePatient(String cnp) {
        log.info("deletePatient() from PatientsServiceImplementation");

        PatientsTable patientsTable = patientsRepository.findPatientsTableByCnp(cnp);
        if( null == patientsTable ){
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }
        patientsTable.setIsactive(false);
        patientsRepository.save(patientsTable);
        EntityModel pseudoDeletedPatient = patientsHateoasSelfParent.toModel(patientsTable);
        return pseudoDeletedPatient;
    }

    /*--------- UTILS ----------*/
    private String formatYYYYMMdd(Date birthdate) {
        LocalDate birthdateLocal = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(birthdateLocal);
    }

    /*
       NAME METHOD: intersect from class PatientsServiceImplementation
       SCOPE: Find the intersection of two collections of patient entities and union their links.

       NOTE:
           Given two collections of patient entities, find the intersection of entities based on content.
           Also, union the links from both collections.

           The method uses lists to find the intersection of entities and retains only common elements.

           It then iterates through the links of the first collection and adds any links from the second
           collection that are not already present in the first collection.

           Finally, it returns a new CollectionModel containing the intersected entities and the union of links.
    */
    public  CollectionModel<EntityModel<PatientsTable>> intersect(
            CollectionModel<EntityModel<PatientsTable>> result1,
            CollectionModel<EntityModel<PatientsTable>> result2) {

        List<EntityModel<PatientsTable>> list1 = new ArrayList<>(result1.getContent());
        List<EntityModel<PatientsTable>> list2 = new ArrayList<>(result2.getContent());

        // Keep just intersection of lists
        list1.retainAll(list2);

        /* Union over links */
        result1.getLinks().forEach(link -> {
            if (!result2.getLinks().contains(link)) {
                result2.add(link);
            }
        });

        return CollectionModel.of(list1, result2.getLinks());
    }
    /*
       NAME METHOD: getBodyWithHateoas from class PatientsServiceImplementation
       SCOPE: Build a CollectionModel with HATEOAS links based on patient entities and custom HATEOAS links.

       NOTE:
           Build a CollectionModel by combining the list of patient entities and custom HATEOAS links.
           Generate custom HATEOAS links for the parent and self-parent based on the provided patientForCustomHateoas.
           If the list of patients is empty, use the links from customHateoasParent as links for the entire collection.
           Otherwise, use the links from customHateoasSelfParent for each individual patient in the collection.
           Return the final CollectionModel with HATEOAS links.
    */
    private CollectionModel<EntityModel<PatientsTable>> getBodyWithHateoas(List<EntityModel<PatientsTable>> listPatients, PatientsTable patientForCustomHateoas  ) {
        Links customHateoasParent= patientsHateoasParent.toModel(new PatientsTable()).getLinks();
        Links customHateoasSelfParent = patientsHateoasSelfParentCustom.toModel(patientForCustomHateoas).getLinks();

        if (listPatients.isEmpty()) {
            return CollectionModel.of(listPatients,customHateoasParent);
        } else {
            return CollectionModel.of(listPatients, customHateoasSelfParent);
        }
    }
}
