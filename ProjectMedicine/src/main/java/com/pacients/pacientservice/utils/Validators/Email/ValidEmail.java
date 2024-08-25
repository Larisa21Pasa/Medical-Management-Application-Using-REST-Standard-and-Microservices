
package com.pacients.pacientservice.utils.Validators.Email;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {
    String message() default "Name must be formed from 1 to 20 letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
