package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.AppointmentsAppointmentTimeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AppointmentsAppointmentTimeValidator.class)
public @interface ValidAppointmentsAppointmentTime {
    String message() default "Appointment data is in invalid format. Search appointments by year+month+day+hours+minutes.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}