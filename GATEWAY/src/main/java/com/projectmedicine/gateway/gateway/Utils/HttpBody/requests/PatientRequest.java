package com.projectmedicine.gateway.gateway.Utils.HttpBody.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    /* This DTO will be used in edit functionality also.
    Some fields will not be active in Frontend side  because user can not modify id field like cnp
     or unique field like email which idm depends on  */
    private String cnp;
    private Integer idUser = -1;
    private String lastname;
    private String firstname;
    private String email;
    private String phone;
    private String birthdate;
    private Boolean isactive = true;
}
