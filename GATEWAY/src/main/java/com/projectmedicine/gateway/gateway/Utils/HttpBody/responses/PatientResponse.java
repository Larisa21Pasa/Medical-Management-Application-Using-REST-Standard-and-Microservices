package com.projectmedicine.gateway.gateway.Utils.HttpBody.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    private String cnp;
    private String lastname;
    private String firstname;
    private String email;
    private String phone;
    private Date birthdate;
    private Boolean isactive;
}