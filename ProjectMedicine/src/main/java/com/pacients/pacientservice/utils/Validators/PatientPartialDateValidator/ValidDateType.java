package com.pacients.pacientservice.utils.Validators.PatientPartialDateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTypeValidator.class)
public @interface ValidDateType {
    String message() default "Date must be a digit accodingly month/day maximun value, type must be month or day.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}