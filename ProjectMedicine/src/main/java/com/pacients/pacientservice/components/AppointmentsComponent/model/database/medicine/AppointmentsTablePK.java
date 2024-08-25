package com.pacients.pacientservice.components.AppointmentsComponent.model.database.medicine;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsTablePK implements Serializable {
    @Column(name = "cnp")
    String cnp;

    @Column(name = "id_doctor")
    Integer idDoctor;

    @Column(name = "appointment_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentTime;

}
