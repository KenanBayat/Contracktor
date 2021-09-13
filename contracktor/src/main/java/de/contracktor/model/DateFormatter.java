package de.contracktor.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private DateFormatter(){}

    public static String format(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String result = date.format(formatter);
        return result;
    }
}
