package com.projectmedicine.gateway.gateway.Utils.HttpBody.responses;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConsultationsResponse {
    private String id;
    private String idPatient;
    private int idDoctor;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentTime;
    private String diagnostic;
    private List<InvestigationsResponse> investigations;
}
