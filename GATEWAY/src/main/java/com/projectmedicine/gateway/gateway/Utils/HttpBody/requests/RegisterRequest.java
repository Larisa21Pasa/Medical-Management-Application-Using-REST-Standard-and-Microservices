package com.projectmedicine.gateway.gateway.Utils.HttpBody.requests;

import com.projectmedicine.gateway.gateway.Utils.Others.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private RoleEnum role;

}
