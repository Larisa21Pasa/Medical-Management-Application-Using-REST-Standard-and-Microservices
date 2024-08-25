package com.projectmedicine.gateway.gateway.SecurityConfigurationComponent;


import com.projectmedicine.gateway.gateway.Utils.Others.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;




@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private static final String[] WHITE_LIST_URL = {
            "/api/medical_office/gateway/patients/register/**",
            "/api/medical_office/gateway/patients/authenticate/**",
            "/api/medical_office/gateway/doctors/register/**",
            "/api/medical_office/gateway/doctors/authenticate/**",
            "/api/medical_office/gateway/logout"

//            ,
//            "/api/medical_office/gateway/messages"

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(
                                "/api/medical_office/gateway/doctors",
                                "/api/medical_office/gateway/doctors/doctor/{idDoctor}",
                                "/api/medical_office/gateway/appointments",
                                "/api/medical_office/gateway/appointments/{cnp}/{idDoctor}/{appointmentTime}",
                                "/api/medical_office/gateway/consultations"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.PATIENT.name(), RoleEnum.DOCTOR.name())

                        .requestMatchers(
                                "/api/medical_office/gateway/patients",
                                "/api/medical_office/gateway/appointments/patient/{cnp}"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.PATIENT.name())

                        .requestMatchers(
                                "/api/medical_office/gateway/doctors/user/{idUser}",
                                "/api/medical_office/gateway/appointments/doctor/{idDoctor}"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.DOCTOR.name())

                        .requestMatchers(
                                "/api/medical_office/gateway/patients/user/{idUser}",
                                "/api/medical_office/gateway/patients/patient/{cnp}",
                                "/api/medical_office/gateway/patients/register/{cnp}",
                                "/api/medical_office/gateway/patients/authenticate",
                                "/api/medical_office/gateway/patients/edit/{cnp}",
                                "/api/medical_office/gateway/patients/{cnp}",
                                "/api/medical_office/gateway/messages",

                                "/api/medical_office/gateway/appointments/patient/{cnp}/doctor/{idDoctor}"

                        ).hasAnyRole(RoleEnum.PATIENT.name())

                        .requestMatchers(
                                "/api/medical_office/gateway/doctors/register",
                                "/api/medical_office/gateway/doctors/authenticate",
                                "/api/medical_office/gateway/doctors/edit/{idDoctor}",
                                "/api/medical_office/gateway/consultations/doctor/consultation/{cnp}/{idDoctor}/{appointmentTime}",
                                "/api/medical_office/gateway/consultations/consultation/{_id}/investigation"

                        ).hasAnyRole(RoleEnum.DOCTOR.name())

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("securityFilterChain() with http {} ", http);
        return http.build();
    }

}
