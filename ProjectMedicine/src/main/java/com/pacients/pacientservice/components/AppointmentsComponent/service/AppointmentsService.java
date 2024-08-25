package com.pacients.pacientservice.components.AppointmentsComponent.service;

import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
//import io.swagger.models.auth.In;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.Date;

public interface AppointmentsService {
    /*--------- GET ------------*/

    CollectionModel<EntityModel<AppointmentsTable>> getAllAppointments();
    //CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsByAppointmenttime(LocalDateTime appointmentTime);
    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsByStatus(String status) ;
    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForSpecificPatient(String cnp) ;
    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForPatientByAppointmentTime(String cnp, Integer date, String type) ;
    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForPatientByStatus(String cnp, String status) ;

    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForSpecificDoctor(Integer idDoctor) ;
    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForDoctorByAppointmentTime(Integer idDoctor, Integer date, String type) ;
    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsForDoctorByStatus(Integer idDoctor, String status) ;

    /*--------- PAGEABLE ------------*/

    CollectionModel<EntityModel<AppointmentsTable>> getAppointmentsByNumberPage(Integer page, Integer itemsPerPage) ;

    /*--------- PUT ------------*/

    EntityModel<AppointmentsTable> addAppointment(AppointmentsTable appointmentsTable);

    EntityModel<AppointmentsTable> updateAppointment(AppointmentsTable appointmentsTable, String cnp, Integer idDoctor) ;
}
