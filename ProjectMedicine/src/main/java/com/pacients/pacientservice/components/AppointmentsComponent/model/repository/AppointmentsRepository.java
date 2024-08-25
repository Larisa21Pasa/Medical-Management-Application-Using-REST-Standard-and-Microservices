package com.pacients.pacientservice.components.AppointmentsComponent.model.repository;

import com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine.AppointmentsTable;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentsRepository  extends CrudRepository<AppointmentsTable, Integer> {
    List<AppointmentsTable> findAll();
    List<AppointmentsTable> findAppointmentsTableByAppointmentsTablePK_AppointmentTime(LocalDateTime appointmentTime);
    AppointmentsTable findByAppointmentsTablePK_CnpAndAndAppointmentsTablePK_IdDoctorAndAndAppointmentsTablePKAppointmentTime(String cnp, Integer idUser, LocalDateTime appointmentTime);
   @Query("SELECT a FROM AppointmentsTable a WHERE DAY(a.appointmentsTablePK.appointmentTime) = :day")
   List<AppointmentsTable> findAppointmentsByDay(@Param("day") Integer day);
    @Query("SELECT a FROM AppointmentsTable a WHERE MONTH(a.appointmentsTablePK.appointmentTime) = :month")
    List<AppointmentsTable> findAppointmentsByMonth(@Param("month") Integer month);
    List<AppointmentsTable> findAppointmentsTableByAppointmentsTablePK_Cnp(String cnp);
    List<AppointmentsTable> findAppointmentsTableByStatusContaining(String status);
    List<AppointmentsTable> findAppointmentsTableByAppointmentsTablePK_IdDoctor(Integer idDoctor);


}