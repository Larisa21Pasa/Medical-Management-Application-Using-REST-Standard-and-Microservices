package com.projectmedicine.gateway.gateway.Utils.HttpBody.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
    private Integer idDoctor=-1;
    private Integer idUser = -1;

    private String lastname;

    private String firstname;

    private String email;

    private String phone;

    private String specialization;
}
