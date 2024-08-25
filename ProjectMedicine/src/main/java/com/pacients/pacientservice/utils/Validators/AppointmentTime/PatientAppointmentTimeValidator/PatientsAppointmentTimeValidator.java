package com.pacients.pacientservice.utils.Validators.AppointmentTime.PatientAppointmentTimeValidator;

import com.pacients.pacientservice.utils.Exceptions.DateTimeValidationException;
import com.pacients.pacientservice.utils.Validators.AppointmentTime.AppointmentsAppointmentTimeValidator.ValidAppointmentsAppointmentTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class PatientsAppointmentTimeValidator implements ConstraintValidator<ValidPatientsAppointmentTime, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null || s.isEmpty()) {
            throw new DateTimeValidationException("Date string is null or empty.");
        }


        try {
            // Define the expected date-time format
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Split the date string by '-' and ' ' to get individual components
            String[] parts = s.split("[\\s-]");

            // Extract year, month, and day
            int givenYear = Integer.parseInt(parts[0]);
            int givenMonth = Integer.parseInt(parts[1]);
            int givenDay = Integer.parseInt(parts[2]);


            System.out.println("Year: " + givenYear);
            System.out.println("Month: " + givenMonth);
            System.out.println("Day: " + givenDay);

            // Check if the year is the current year
            int currentYear = ZonedDateTime.now().getYear();
            if (givenYear < currentYear) {
                throw new DateTimeValidationException("Year is not the current year.");
            }

            // Check if the month is in the range [1, 12]
            if (givenMonth < 1 || givenMonth > 12) {
                throw new DateTimeValidationException("Month is not in the valid range [1, 12].");
            }

            // Check if the day is valid for the given month
            int maxDaysInMonth = YearMonth.of(givenYear, givenMonth).lengthOfMonth();
            System.out.println("maxdaysmonts: "+maxDaysInMonth);
            if (givenDay < 1 || givenDay > maxDaysInMonth) {
                throw new DateTimeValidationException("Day is not valid for the given month.");
            }


            // Check if the date is in the future or within the next 15 minutes
            // Parse the input string into ZonedDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            LocalDateTime minValidDateTime = LocalDateTime.now().plusMinutes(15);
            System.out.println("over 15 min "+minValidDateTime);
            if (localDateTime.isBefore(minValidDateTime)) {
                throw new DateTimeValidationException("Date '" + s + "' is not at least 15 minutes in the future. You need to make appointent at least at "+minValidDateTime);
            }

            return true;
        } catch (DateTimeParseException e) {
            throw new DateTimeValidationException("Invalid date format.", e);
        }
    }
}