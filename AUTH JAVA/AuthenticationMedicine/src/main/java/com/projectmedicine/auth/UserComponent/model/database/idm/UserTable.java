package com.projectmedicine.auth.UserComponent.model.database.idm;
import com.projectmedicine.auth.UserComponent.model.database.idm.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;


@Data
@Builder(toBuilder = true)
//@ToString
@ToString(exclude = {"tokens"})

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "Users")
public class UserTable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Email
    @Basic
    @Column(name = "email",
            unique = true)
    private String email;

    @NotNull
    @Basic
    @Column(name = "hashed_password")
    private String hashedPassword;


    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @OneToMany(mappedBy = "userTable")
    private List<TokenTable> tokens;

    /* Methods from UserDetails */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleEnum.getAuthorities();
    }
    /* Necessary for authentication part */
    @Override
    public String getUsername() { return email; }

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

