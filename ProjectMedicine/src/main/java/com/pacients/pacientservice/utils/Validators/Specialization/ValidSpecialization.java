package com.pacients.pacientservice.utils.Validators.Specialization;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@NotNull
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpecializationValidator.class)
public @interface ValidSpecialization {
    String message() default "Specialization must be from a specific list.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
