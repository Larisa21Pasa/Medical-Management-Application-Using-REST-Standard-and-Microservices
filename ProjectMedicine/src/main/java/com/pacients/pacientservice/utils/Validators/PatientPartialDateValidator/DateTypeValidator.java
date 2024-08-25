package com.pacients.pacientservice.utils.Validators.PatientPartialDateValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateTypeValidator implements ConstraintValidator<ValidDateType, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {

            String pattern = "^\\d{13}$";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(s);
            return matcher.matches();
        }
    }
}
