package com.projectmedicine.auth.UserComponent.model.database.idm.enums;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public enum RoleEnum {
    PATIENT,
    DOCTOR,
    ADMIN;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new SimpleGrantedAuthority( "ROLE_" + this.name() );

        log.info("getAuthorities=> {}", authorities);
        return Collections.singletonList(authorities);
    }
}


