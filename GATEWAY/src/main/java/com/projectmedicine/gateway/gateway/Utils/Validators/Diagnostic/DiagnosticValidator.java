package com.projectmedicine.gateway.gateway.Utils.Validators.Diagnostic;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DiagnosticValidator implements ConstraintValidator<ValidDiagnostic, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            try {
                // Attempt to parse the string to the EnumSpecializations enum
                EnumDiagnostic.valueOf(s.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}