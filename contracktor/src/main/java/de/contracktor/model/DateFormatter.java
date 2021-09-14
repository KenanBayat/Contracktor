package de.contracktor.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    public DateFormatter(){}
    //format the date
    public static String format(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'.' LLLL yyyy");
        //String result = date.format(formatter);
        return formatter.format(date);
    }
}
