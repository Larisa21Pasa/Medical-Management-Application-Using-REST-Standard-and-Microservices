package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.String;

import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.IDPatient.ValidIDPatient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringValidator implements ConstraintValidator<ValidString, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            String pattern = "^[a-zA-Z]+(\\s[a-zA-Z]+)*$";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(s);
            return matcher.matches();
        }
    }
}
