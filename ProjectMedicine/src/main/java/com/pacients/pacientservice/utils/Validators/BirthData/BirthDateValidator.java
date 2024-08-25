package com.pacients.pacientservice.utils.Validators.BirthData;

import com.pacients.pacientservice.utils.Exceptions.DateTimeValidationException;
import com.pacients.pacientservice.utils.Exceptions.UnprocessableContentException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null || s.isEmpty()) {
            throw new DateTimeValidationException("Date string is null or empty.");
        }

        try {
            // Parse the input string into LocalDate
            LocalDate birthDate = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Calculate the minimum valid birth date (18 years ago from the current date)
            LocalDate minValidBirthDate = LocalDate.now().minusYears(18);

            System.out.println("Minimum valid birth date: " + minValidBirthDate);

            // Check if the provided birth date is at least 18 years ago
            if (birthDate.isAfter(minValidBirthDate)) {
                throw new UnprocessableContentException("Patient must be at least 18 years old. Birth date '" + s + "' is not valid.");
            }

            return true;
        } catch (DateTimeParseException e) {
            throw new DateTimeValidationException("Invalid date format.", e);
        }
    }
}