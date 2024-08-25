
package com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pacients.pacientservice.components.DoctorsComponent.model.database.medicine.DoctorsTable;
import com.pacients.pacientservice.components.PatientsComponent.model.database.medicine.PatientsTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsTable {
   @EmbeddedId
   private AppointmentsTablePK appointmentsTablePK = new AppointmentsTablePK();

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("cnp")
    @JoinColumn(name = "cnp")
    @JsonIgnore
    PatientsTable patient;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("idDoctor")
    @JoinColumn(name = "id_doctor")
    @JsonIgnore
    DoctorsTable doctor;

    @Column(name = "status")
    private String status;

    public AppointmentsTable(String cnp, Integer idDoctor, String status, LocalDateTime
            appointmentTime, PatientsTable patient, DoctorsTable doctor) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentsTablePK.setCnp(cnp);
        this.appointmentsTablePK.setIdDoctor(idDoctor);
        this.appointmentsTablePK.setAppointmentTime(appointmentTime);

        this.status = status;

    }

}