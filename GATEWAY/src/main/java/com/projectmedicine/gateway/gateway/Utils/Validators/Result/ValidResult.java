package com.projectmedicine.gateway.gateway.Utils.Validators.Result;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResultValidator.class)
public @interface ValidResult {
    //TODO SA ADAUG MESAJ CUSTOM POTRIVIT
    String message() default "Name must be formed from 1 to 20 letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}