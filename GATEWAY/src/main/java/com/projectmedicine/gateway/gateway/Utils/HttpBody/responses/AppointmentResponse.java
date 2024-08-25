package com.projectmedicine.gateway.gateway.Utils.HttpBody.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponse  extends RepresentationModel<AppointmentResponse> {

    private AppointmentPKDTO appointmentsTablePK;
    private String status;


    public static class AppointmentPKDTO {

        private String cnp;
        private Integer idDoctor;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime appointmentTime;

    }
}