package com.pacients.pacientservice.utils.Validators.Specialization;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpecializationValidator implements ConstraintValidator<ValidSpecialization, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            try {
                // Attempt to parse the string to the EnumSpecializations enum
                EnumSpecializations.valueOf(s.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}