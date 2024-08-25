package com.pacients.pacientservice.utils.Validators.StatusAppointment;

import com.pacients.pacientservice.utils.Validators.Specialization.SpecializationValidator;
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
@Constraint(validatedBy = StatusValidator.class)
public @interface ValidStatus {
    String message() default "Status must be 'Canceled', 'Honored', 'Missed'. ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
