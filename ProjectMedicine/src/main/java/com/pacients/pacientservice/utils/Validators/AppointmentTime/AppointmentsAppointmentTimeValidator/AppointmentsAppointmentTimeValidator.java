package com.pacients.pacientservice.utils.Validators.AppointmentTime.AppointmentsAppointmentTimeValidator;

import com.pacients.pacientservice.utils.Exceptions.DateTimeValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class AppointmentsAppointmentTimeValidator implements ConstraintValidator<ValidAppointmentsAppointmentTime, String> {

    private static final DateTimeFormatter formatterYearMonthDayHourMinutes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null || s.isEmpty()) {
            throw new DateTimeValidationException("Date string is null or empty.");
        }

        try {
            LocalDateTime localDateTime = LocalDateTime.parse(s, formatterYearMonthDayHourMinutes);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

            // Check if the ZonedDateTime is valid
            // If it's not valid, it will throw a DateTimeException
            zonedDateTime.toInstant(); // This line is just to trigger the check

            return true;
        } catch (DateTimeException e) {
            throw new DateTimeValidationException("Invalid date and time format. Please provide the date in the format 'yyyy-MM-dd HH:mm'.", e);
        }
    }
}
