package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.ProcessingDuration;

import com.projectmedicine.mongo.projectmedicinemongodb.Utils.Validators.IDPatient.ValidIDPatient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProcessingDurationValidator implements ConstraintValidator<ValidProcessingDuration, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        } else {
            /* Maximum of 100 hour of processing one investigation */
            String pattern = "^[1-9][0-9]?$|^100";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(s);
            return matcher.matches();
        }
    }
}
