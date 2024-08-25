package com.projectmedicine.gateway.gateway.Utils.HttpBody.responses;

import com.projectmedicine.gateway.gateway.Utils.Others.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements UserDetails {
    private Integer userId;
    private String email;
    private String hashedPassword;
    private RoleEnum roleEnum;

    /* Methods from UserDetails */

    /* Necessary for authentication part */
    @Override
    public String getUsername() { return email; }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return roleEnum.getAuthorities();
}

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* Method that indicates whether the user account is locked or not.
       This method is used by the authentication system to check if the user account is locked. */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* Method that indicates whether the user is enabled or disabled.
       This method is used by the authentication system to check if the user is enabled. */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

