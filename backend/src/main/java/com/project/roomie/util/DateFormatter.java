package com.project.roomie.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateFormatter {

    static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Named("toLocalDate")
    public LocalDate toLocalDate(String dataString) {
        try {
            return LocalDate.parse(dataString, FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Data inválida: " + dataString);
            return null;
        }
    }

    @Named("toStringDate")
    public String toStringDate(LocalDate data) {
        if (data == null) return null;
        return data.format(FORMATTER);
    }
}