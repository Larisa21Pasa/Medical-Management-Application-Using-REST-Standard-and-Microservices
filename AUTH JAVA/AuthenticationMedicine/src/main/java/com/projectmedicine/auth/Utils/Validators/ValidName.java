/**************************************************************************

 File:        ValidName.java
 Copyright:   (c) 2023 NazImposter
 Description: Custom annotation for validating names.
 Designed by: Sebastian Pitica

 Module-History:
 Date        Author                Reason
 07.11.2023  Sebastian Pitica      Structure and functionality
 29.11.2023  Sebastian Pitica      Added description

 **************************************************************************/

package com.projectmedicine.auth.Utils.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface ValidName {
    String message() default "Name must be formed from 1 to 20 letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}