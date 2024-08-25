package com.pacients.pacientservice.components.DoctorsComponent.service;

import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.Date;
import java.util.Optional;

public interface DoctorsService {
    /*--------- GET ------------*/
     CollectionModel<EntityModel<DoctorsTable>> getAllDoctors() ;
    EntityModel<DoctorsTable> getDoctorByIdDoctor( Integer idDoctor);
    CollectionModel<EntityModel<DoctorsTable>> getDoctorByFirstame(String firstname);
    CollectionModel<EntityModel<DoctorsTable>> getDoctorByLastame(String lastname);

    CollectionModel<EntityModel<DoctorsTable>> getDoctorByEmail(String email) ;
    CollectionModel<EntityModel<DoctorsTable>> getDoctorByPhone(String phone) ;
    CollectionModel<EntityModel<DoctorsTable>> getDoctorBySpecialization(String specialization) ;
    CollectionModel<EntityModel<DoctorsTable>> intersectFilteredDoctors(
            CollectionModel<EntityModel<DoctorsTable>> result1,
            CollectionModel<EntityModel<DoctorsTable>> result2);

    /*--------- PAGEABLE ------------*/
    CollectionModel<EntityModel<DoctorsTable>> getDoctorsByNumberPage(Integer page, Integer itemsPerPage) ;


    /*--------- PUT ------------*/
    EntityModel<DoctorsTable> addDoctor(DoctorsTable doctorsTable);

    /*--------- POST ------------*/
    EntityModel<DoctorsTable> updateDoctor(DoctorsTable doctorsTable, Integer idDoctor) ;

    /*--------- DELETE ------------*/
    EntityModel<DoctorsTable> deleteDoctor(String cnp);

}
