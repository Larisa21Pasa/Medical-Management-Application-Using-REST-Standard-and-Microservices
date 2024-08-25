package com.pacients.pacientservice.components.DoctorsComponent.service;

import com.pacients.pacientservice.components.DoctorsComponent.controller.DoctorsController;
import com.pacients.pacientservice.components.DoctorsComponent.hateoas.Custom.DoctorsHateoasSelfParentCustom;
import com.pacients.pacientservice.components.DoctorsComponent.hateoas.Parent.DoctorsHateoasParent;
import com.pacients.pacientservice.components.DoctorsComponent.hateoas.SelfParent.DoctorsHateoasSelfParent;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.DoctorsComponent.model.repository.DoctorsRepository;
import com.pacients.pacientservice.utils.Exceptions.ConflictException;
import com.pacients.pacientservice.utils.Exceptions.NotAcceptableException;
import com.pacients.pacientservice.utils.Exceptions.NotFoundException;
import com.pacients.pacientservice.utils.Exceptions.UnprocessableContentException;
import com.pacients.pacientservice.utils.Validators.Email.EmailValidator;
import com.pacients.pacientservice.utils.Validators.Name.NameValidator;
import com.pacients.pacientservice.utils.Validators.Phone.PhoneValidator;
import com.pacients.pacientservice.utils.Validators.Specialization.SpecializationValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.pacients.pacientservice.utils.Logs.ProgramLogs.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@Service
public class DoctorsServiceImplementation implements  DoctorsService{
    @Autowired
    private DoctorsRepository doctorsRepository;

    private final NameValidator nameValidator = new NameValidator();
    private final EmailValidator emailValidator = new EmailValidator();
    private final PhoneValidator phoneValidator = new PhoneValidator();
    private final SpecializationValidator specializationValidator = new SpecializationValidator();
    @Autowired
    private DoctorsHateoasParent doctorsHateoasParent;
    @Autowired
    private DoctorsHateoasSelfParent doctorsHateoasSelfParent;
    @Autowired
    private DoctorsHateoasSelfParentCustom doctorsHateoasSelfParentCustom;

    /*
         NAME METHOD: getAllDoctors from class DoctorsServiceImplementation
         SCOPE: Retrieve all doctor information with HATEOAS links.
      */
    @Override
    public CollectionModel<EntityModel<DoctorsTable>> getAllDoctors() {
        log.info("getAllDoctors() from DoctorsServiceImplementation");
        /* Doctor resource + SelfParent Hateoas */
        List<EntityModel<DoctorsTable>> listDoctors = new ArrayList<>();

        /* Find Doctor resource and associate each data with hateoas component */
        List<DoctorsTable> doctors = doctorsRepository.findAll();
        for (DoctorsTable doctor : doctors) {
            EntityModel<DoctorsTable> entityModel = doctorsHateoasSelfParent.toModel(doctor);
            listDoctors.add(entityModel);
        }
        return CollectionModel.of(listDoctors);
    }

    /*
       NAME METHOD: getDoctorByIdDoctor from class DoctorsServiceImplementation
       SCOPE: Retrieve doctor information by IdDoctor with HATEOAS links.

       HTTP CODES:
             *** 404 -> Not Found
             - No doctor found for the given id_doctor triggers a 404 response.
    */
    @Override
    public EntityModel<DoctorsTable> getDoctorByIdDoctor(Integer idDoctor) {
        /* Here I think I need maybe a validation for idDoctor to be digit. */
        DoctorsTable doctor = doctorsRepository.findDoctorsTableByIdDoctor(idDoctor);
        if (null == doctor) {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }
        else
        {
            return doctorsHateoasSelfParent.toModel(doctor);
        }
    }


    /*
     NAME METHOD: getDoctorByFirstame from class DoctorsServiceImplementation
     SCOPE: Retrieve a collection of doctor information by firstname with HATEOAS links.

     HTTP CODES:
       *** 422 -> Unprocessable Entity
           - Invalid firstname syntax results in a 422 response.

     NOTE:
     When no matching doctors are found, an empty collection is returned.
     This design choice is made as an empty collection is still a valid object .
  */
    public CollectionModel<EntityModel<DoctorsTable>> getDoctorByFirstame(String firstname){
        log.info("getDoctorByFirstame() from DoctorsServiceImplementation");
        if(!nameValidator.isValid(firstname, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<DoctorsTable> doctors = doctorsRepository.findDoctorsTableByFirstnameContaining(firstname);
        List<EntityModel<DoctorsTable>> listDoctors = doctors.stream()
                .map(doctorsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        DoctorsTable doctorForCustomHateoas = new DoctorsTable();
        doctorForCustomHateoas.setFirstname(firstname);

        return  getBodyWithHateoas(listDoctors, doctorForCustomHateoas);
    }

    /*
       NAME METHOD: getDoctorByLastame from class DoctorsServiceImplementation
       REST OF EXPLANATIONS: same as getDoctorByFirstame
    */
    public CollectionModel<EntityModel<DoctorsTable>> getDoctorByLastame(String lastname) {
        log.info("getDoctorByLastame() from DoctorsServiceImplementation");

        if(!nameValidator.isValid(lastname, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<DoctorsTable> doctors = doctorsRepository.findDoctorsTableByLastnameContaining(lastname);
        List<EntityModel<DoctorsTable>> listDoctors = doctors.stream()
                .map(doctorsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        DoctorsTable doctorForCustomHateoas = new DoctorsTable();
        doctorForCustomHateoas.setLastname(lastname);

        return  getBodyWithHateoas(listDoctors, doctorForCustomHateoas);
    }


    /*
      NAME METHOD: getDoctorByEmail from class DoctorsServiceImplementation
      REST OF EXPLANATIONS: same as getDoctorByFirstame
   */
    @Override
    public CollectionModel<EntityModel<DoctorsTable>> getDoctorByEmail(String email) {
        log.info("getDoctorByEmail() from DoctorsServiceImplementation");

        if(!emailValidator.isValid(email, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<DoctorsTable> doctors = doctorsRepository.findDoctorsTableByEmailContaining(email);
        List<EntityModel<DoctorsTable>> listDoctors = doctors.stream()
                .map(doctorsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        DoctorsTable doctorForCustomHateoas = new DoctorsTable();
        doctorForCustomHateoas.setEmail(email);

        return  getBodyWithHateoas(listDoctors, doctorForCustomHateoas);
    }

    /*
     NAME METHOD: getDoctorByPhone from class DoctorsServiceImplementation
     REST OF EXPLANATIONS: same as getDoctorByFirstame
    */
    @Override
    public CollectionModel<EntityModel<DoctorsTable>> getDoctorByPhone(String phone) {
        log.info("getDoctorByPhone() from DoctorsServiceImplementation");

        if (!phoneValidator.isValid(phone, null)) {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        DoctorsTable doctor = doctorsRepository.findDoctorsTableByPhone(phone);
        EntityModel<DoctorsTable> entityModel = doctorsHateoasSelfParent.toModel(doctor);

        DoctorsTable doctorForCustomHateoas = new DoctorsTable();
        doctorForCustomHateoas.setPhone(phone);

        // Create a list containing the single entityModel
        List<EntityModel<DoctorsTable>> entityModelList = Collections.singletonList(entityModel);

        // Call getFilteredBody with the list
        return getBodyWithHateoas(entityModelList, doctorForCustomHateoas);
    }

    /*
    NAME METHOD: getDoctorBySpecialization from class DoctorsServiceImplementation
    REST OF EXPLANATIONS: same as getDoctorByFirstame
   */
    @Override
    public CollectionModel<EntityModel<DoctorsTable>> getDoctorBySpecialization(String specialization) {
        log.info("getDoctorBySpecialization() from DoctorsServiceImplementation");

        if(!specializationValidator.isValid(specialization, null))
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY);
        }

        List<DoctorsTable> doctors = doctorsRepository.findDoctorsTableBySpecializationContaining(specialization);
        List<EntityModel<DoctorsTable>> listDoctors = doctors.stream()
                .map(doctorsHateoasSelfParent::toModel)
                .collect(Collectors.toList());

        DoctorsTable doctorForCustomHateoas = new DoctorsTable();
        doctorForCustomHateoas.setSpecialization(specialization);

        return  getBodyWithHateoas(listDoctors, doctorForCustomHateoas);
    }




    /*--------- POST ----------*/
     /*
       NAME METHOD: addDoctor from class DoctorsServiceImplementation
       SCOPE: Add a new doctor with input validation and potential conflict checks.

       HTTP CODES:
         *** 406 -> Not Acceptable
             - Returned if there are missing or invalid input data.

         *** 422 -> Unprocessable Entity
             - Returned if input field validation fails.

         *** 409 -> Conflict
             - Returned if there is an attempt to add a doctor with the same email but a different id.

    */
    @Override
    public EntityModel<DoctorsTable> addDoctor(DoctorsTable doctorsTable) {
        log.info("addDoctor() from DoctorsServiceImplementation");

         /*
           If there are missing or invalid input data, returning HTTP status code 406 (Not Acceptable)
           is appropriate, as it signifies that the server cannot process and respond to the client's
           request due to the absence or incorrectness of the provided data.
        */
        if(
                null == doctorsTable.getIdUser()
                || null == doctorsTable.getLastname()
                || null == doctorsTable.getFirstname()
                || null == doctorsTable.getEmail()
                || null == doctorsTable.getPhone()
                || null == doctorsTable.getSpecialization()
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
                !nameValidator.isValid(doctorsTable.getLastname(), null)
                || !nameValidator.isValid(doctorsTable.getFirstname(), null)
                || !emailValidator.isValid(doctorsTable.getEmail(), null)
                || !phoneValidator.isValid(doctorsTable.getPhone(), null)
                || ! specializationValidator.isValid(doctorsTable.getSpecialization(),null)
        )
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        /*
           Check for an existing doctor with the same email but a different IdDoctor.
           If found, throw a ConflictException with HTTP status code 409.
        */

        DoctorsTable duplicateDoctor = doctorsRepository.findDoctorsTableByEmail(doctorsTable.getEmail());
        if(  null != duplicateDoctor  )
        {
            throw new ConflictException(RESOURCE_ALREADY_EXIST);
        }

        try{
            doctorsRepository.save(doctorsTable);

        }catch ( DataAccessException dataAccessException)
        {
            throw  new RuntimeException(SAVE_ERROR + dataAccessException.getRootCause());
        }

        doctorsTable = doctorsRepository.findDoctorsTableByEmail(doctorsTable.getEmail());
        return doctorsHateoasSelfParent.toModel(doctorsTable);
    }


    /*
        NAME METHOD: updateDoctor from class DoctorsServiceImplementation
        SCOPE: Update an existing doctor with input validation.

        HTTP CODES:
          *** 404 -> Not Found
              - Returned if the doctor to be updated is not found.

          *** 422 -> Unprocessable Entity
              - Returned if there are missing or invalid input data.

          *** 406 -> Not Acceptable
              - Returned if there are missing or invalid input data during the  update.

     */
    @Override
    public EntityModel<DoctorsTable> updateDoctor(DoctorsTable doctorsTable, Integer idDoctor) {
        log.info("updateDoctor() from DoctorsServiceImplementation");

        /* Check resource to be existent */
//        if(null == doctorsTable.getIdDoctor())
//        {
//            throw new NotFoundException(NOT_FOUND_EXCEPTION);
//        }
        if(null == doctorsRepository.findDoctorsTableByIdDoctor(doctorsTable.getIdDoctor()))
        {
            throw new NotFoundException(NOT_FOUND_EXCEPTION);
        }

        /*
         * After confirming the existence of a doctor to work with,
         *  further validation is performed on the rest of the fields.
         */
        if(
                null == doctorsTable.getIdUser()
                || null == doctorsTable.getLastname()
                || null == doctorsTable.getFirstname()
                || null == doctorsTable.getEmail()
                || null == doctorsTable.getPhone()
                || null == doctorsTable.getSpecialization()
        )
        {
            throw new NotAcceptableException(NOT_ACCEPTABLE_EXCEPTION);
        }

        /*
         * After ensuring that all fields are present and a 406 error is not required,
         *  the validity of the field values is checked.
         * If any of the fields is not valid, an Unprocessable Content Exception is thrown.
         */
        if(
                !nameValidator.isValid(doctorsTable.getLastname(), null)
                || !nameValidator.isValid(doctorsTable.getFirstname(), null)
                || !emailValidator.isValid(doctorsTable.getEmail(), null)
                || !phoneValidator.isValid(doctorsTable.getPhone(), null)
                || ! specializationValidator.isValid(doctorsTable.getSpecialization(),null)
        )
        {
            throw new UnprocessableContentException(UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS);
        }

        try{
                log.info("not delete doctor and create updated");
                doctorsRepository.save(doctorsTable);

        }catch (DataAccessException dataAccessException)
        {
            throw  new RuntimeException(UPDATE_ERROR + dataAccessException.getRootCause());
        }

        doctorsTable = doctorsRepository.findDoctorsTableByIdDoctor(doctorsTable.getIdDoctor());
        return doctorsHateoasSelfParent.toModel(doctorsTable);

    }

    @Override
    public EntityModel<DoctorsTable> deleteDoctor(String cnp) {
        return null;
    }

    /*
       NAME METHOD: getDoctorsByNumberPage from class DoctorsServiceImplementation
       SCOPE: Retrieve a paginated list of doctors with HATEOAS links.
       PATH: "/doctors"
       NOTE:
           Retrieve a paginated list of doctors based on the specified page and itemsPerPage parameters.
           Calculate the range of indices for the requested page.
           Iterate through the doctors list and add the corresponding doctors to the result list.

           Generate HATEOAS links for self, parent, next, and previous pages.
           If there are no elements on the requested page and it's not the first page, return links for the previous page.
           If it's the first page, return links for the next page.
           If it's the last page, return links for the previous page.
           Otherwise, return links for both next and previous pages.

           Return a CollectionModel with the appropriate HATEOAS links.
    */
    @Override
    public CollectionModel<EntityModel<DoctorsTable>> getDoctorsByNumberPage(Integer page, Integer itemsPerPage) {
        log.info("getDoctorsByNumberPage() from DoctorsServiceImplementation");

        List<DoctorsTable> listPageableDoctors = doctorsRepository.findAll();
        Integer index_start = page * itemsPerPage;
        Integer index_stop = index_start + itemsPerPage;

        List<EntityModel<DoctorsTable>> listResult = new ArrayList<>();
        for (Integer index = index_start; index < index_stop; index++) {
            if (index < listPageableDoctors.size())
                listResult.add(doctorsHateoasSelfParent.toModel(listPageableDoctors.get(index)));
        }
        Link selfLink = linkTo(methodOn(DoctorsController.class).
                getAllDoctors(Optional.of(page), Optional.of(itemsPerPage), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                .withSelfRel();
        Optional<Integer> nextPage = Optional.of(page + 1);
        Optional<Integer> prevPage = Optional.of(page - 1);
        log.info("NEXTPAGE = {}", nextPage);
        log.info("PR4EVPAGE = {}", prevPage);
        Link nextLink = linkTo(methodOn(DoctorsController.class).
                getAllDoctors(nextPage, Optional.of(itemsPerPage), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                .withRel("next");

        Link prevLink = linkTo(methodOn(DoctorsController.class).
                getAllDoctors(prevPage, Optional.of(itemsPerPage), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                .withRel("prev");

        Link parentLink = linkTo(methodOn(DoctorsController.class).
                getAllDoctors(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                .withRel("parent");

        if (listResult.size() == 0 && page != 0) {

            prevPage = Optional.of(listPageableDoctors.size() / itemsPerPage - 1);
            prevLink = linkTo(methodOn(DoctorsController.class).
                    getAllDoctors(prevPage, Optional.of(itemsPerPage), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
                    .withRel("prev");

            return CollectionModel.of(listResult, parentLink, prevLink);
        }

        if (page == 0) {
            CollectionModel<EntityModel<DoctorsTable>> result = CollectionModel.of(listResult, selfLink, parentLink, nextLink);
            return result;
        }
        log.info("listPageableDoctors.size() {}",listPageableDoctors.size());
        log.info("page {}, (listPageableDoctors.size() / itemsPerPage-1 ) {}",page,(listPageableDoctors.size() / itemsPerPage-1 ));
        if (page == Math.ceil(listPageableDoctors.size() / itemsPerPage))
        {
            log.info("sunt in fis");
            CollectionModel<EntityModel<DoctorsTable>> result = CollectionModel.of(listResult, selfLink, parentLink, prevLink);
            return result;
        }



        CollectionModel<EntityModel<DoctorsTable>> result = CollectionModel.of(listResult, selfLink, parentLink, nextLink, prevLink);
        return result;
    }

    public ResponseEntity<?> provideHeateoasForPageable( Integer itemsPerPage)
    {
       return doctorsHateoasSelfParent.provideHeateoasForPageable(itemsPerPage);
    }


    /*------UTILS-----*/
    private CollectionModel<EntityModel<DoctorsTable>> getBodyWithHateoas(List<EntityModel<DoctorsTable>> listDoctor, DoctorsTable doctorForCustomHateoas  ) {
        Links customHateoasParent= doctorsHateoasParent.toModel(new DoctorsTable()).getLinks();
        Links customHateoasSelfParent = doctorsHateoasSelfParentCustom.toModel(doctorForCustomHateoas).getLinks();

        if (listDoctor.isEmpty()) {
            log.info("list empyy");
            return CollectionModel.of(listDoctor,customHateoasParent);
        } else {
            log.info("list not empty");
            return CollectionModel.of(listDoctor, customHateoasSelfParent);
        }
    }

    public CollectionModel<EntityModel<DoctorsTable>> intersectFilteredDoctors(
            CollectionModel<EntityModel<DoctorsTable>> result1,
            CollectionModel<EntityModel<DoctorsTable>> result2) {


        List<EntityModel<DoctorsTable>> list1 = new ArrayList<>(result1.getContent());
        List<EntityModel<DoctorsTable>> list2 = new ArrayList<>(result2.getContent());

        /* Intersection over resources */
        list1.retainAll(list2);

        /* Union over links */
        result1.getLinks().forEach(link -> {
            if (!result2.getLinks().contains(link)) {
                result2.add(link);
            }
        });

        return CollectionModel.of(list1, result2.getLinks());
    }
}
