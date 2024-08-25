package com.projectmedicine.gateway.gateway.Utils.Validators.IDPatient;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IDPatientValidator implements ConstraintValidator<ValidIDPatient, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            //TODO DE SCHIMBAT LA 13
            String pattern = "^\\d{13}$";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(s);
            return matcher.matches();
        }
    }
}
