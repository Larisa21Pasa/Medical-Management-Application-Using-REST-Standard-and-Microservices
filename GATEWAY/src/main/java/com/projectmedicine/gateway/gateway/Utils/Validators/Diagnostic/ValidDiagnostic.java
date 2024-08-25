package com.projectmedicine.gateway.gateway.Utils.Validators.Diagnostic;

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
@Constraint(validatedBy = DiagnosticValidator.class)
public @interface ValidDiagnostic {
    //TODO SA ENGLEZESC SI AICI
    String message() default "Diagnostic must be santos/bolnav.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
