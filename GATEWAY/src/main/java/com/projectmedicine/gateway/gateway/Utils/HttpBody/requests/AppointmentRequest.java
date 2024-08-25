package com.projectmedicine.gateway.gateway.Utils.HttpBody.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private AppointmentsTablePK appointmentsTablePK ;
    private String status;

}
