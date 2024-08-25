package com.pacients.pacientservice.utils.Validators.Phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            String pattern = "^07[2457]{1}[0-9]{7}$";

           // String pattern = "^(00)?407\\d{8}|07\\d{8}$";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(s);
            return matcher.matches();
        }
    }
}