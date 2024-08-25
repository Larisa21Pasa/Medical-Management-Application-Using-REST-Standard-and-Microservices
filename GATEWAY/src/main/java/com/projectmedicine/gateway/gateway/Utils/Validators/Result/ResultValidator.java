package com.projectmedicine.gateway.gateway.Utils.Validators.Result;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class ResultValidator implements ConstraintValidator<ValidResult, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            try {
                // Attempt to parse the string to the EnumSpecializations enum
                EnumResult.valueOf(s.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}