package com.pacients.pacientservice.utils.Validators.AppointmentTime.PatientAppointmentTimeValidator;

import com.pacients.pacientservice.utils.Validators.AppointmentTime.AppointmentsAppointmentTimeValidator.AppointmentsAppointmentTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatientsAppointmentTimeValidator.class)
public @interface ValidPatientsAppointmentTime {
    String message() default "Appointment time  must be in valid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}