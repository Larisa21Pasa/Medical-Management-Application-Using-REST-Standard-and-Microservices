package com.projectmedicine.gateway.gateway.Utils.HttpBody.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvestigationsResponse {
    private String id;
    private String naming;
    private int processingHours;
    private String result;
}
