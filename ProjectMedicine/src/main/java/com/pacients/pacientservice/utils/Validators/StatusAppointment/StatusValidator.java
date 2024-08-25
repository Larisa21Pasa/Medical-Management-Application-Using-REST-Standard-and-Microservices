package com.pacients.pacientservice.utils.Validators.StatusAppointment;

import com.pacients.pacientservice.utils.Validators.Specialization.EnumSpecializations;
import com.pacients.pacientservice.utils.Validators.Specialization.ValidSpecialization;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            try {
                // Attempt to parse the string to the EnumSpecializations enum
                EnumStatus.valueOf(s.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}