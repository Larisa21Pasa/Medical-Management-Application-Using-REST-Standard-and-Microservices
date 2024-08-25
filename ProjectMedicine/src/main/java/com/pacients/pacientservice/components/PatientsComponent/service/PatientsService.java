package com.pacients.pacientservice.components.PatientsComponent.service;

import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientsService {
    /*--------- GET ------------*/
    CollectionModel<EntityModel<PatientsTable>> getAllPatients();
    EntityModel<PatientsTable> getPatientByCnp( String cnp);
    CollectionModel<EntityModel<PatientsTable>> getPatientByFirstame(String firstname);
    CollectionModel<EntityModel<PatientsTable>> getPatientByLasttame(String lastname);
    CollectionModel<EntityModel<PatientsTable>> getPatientByEmail(String email) ;
    CollectionModel<EntityModel<PatientsTable>> getPatientByPhone(String phone) ;
    CollectionModel<EntityModel<PatientsTable>> getPatientByBirthdate(Date birthdate) ;
    CollectionModel<EntityModel<PatientsTable>> intersect(
            CollectionModel<EntityModel<PatientsTable>> result1,
            CollectionModel<EntityModel<PatientsTable>> result2);
    /*--------- PUT ------------*/
    EntityModel<PatientsTable> addPatient(PatientsTable patientsTable);
    EntityModel<PatientsTable> updatePatient(PatientsTable patientsTable, String cnp) ;

    /*--------- DELETE ------------*/
    EntityModel<PatientsTable> deletePatient(String cnp);

}
