package com.projectmedicine.auth.AuthenticationComponent.service.Logout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public interface LogoutService {
        //extends LogoutHandler {
    public Authentication.LogoutSuccessfulResponseProto logout(String jwt);
//    public void logout(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication
//    );
}
