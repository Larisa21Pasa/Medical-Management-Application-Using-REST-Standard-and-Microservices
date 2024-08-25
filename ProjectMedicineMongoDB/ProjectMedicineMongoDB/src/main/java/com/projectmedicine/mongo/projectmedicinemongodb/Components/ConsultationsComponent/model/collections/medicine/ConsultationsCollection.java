package com.projectmedicine.mongo.projectmedicinemongodb.Components.ConsultationsComponent.model.collections.medicine;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "consultations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationsCollection {

    @Id

    private String _id;


    @Field(name = "idPatient")
    private String idPatient;

    @Field(name = "idDoctor")
    private int idDoctor;

    @Field(name = "appointmentTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentTime;

    @Field(name = "diagnostic")
    private String diagnostic;

    @Field(name = "investigations")
    private List<InvestigationsCollection> investigations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultationsCollection that = (ConsultationsCollection) o;
        return Objects.equals(_id, that._id) &&
                Objects.equals(idPatient, that.idPatient) &&
                Objects.equals(idDoctor, that.idDoctor) &&
                Objects.equals(appointmentTime, that.appointmentTime) &&
                Objects.equals(diagnostic, that.diagnostic) &&
                Objects.equals(investigations, that.investigations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, idPatient, idDoctor, appointmentTime, diagnostic, investigations);
    }

}