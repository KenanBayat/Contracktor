package de.contracktor.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateFormatter {
	public DateFormatter() {
	}

	// format the date
	public static String format(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'.' LLLL yyyy");
		// String result = date.format(formatter);
		return formatter.format(date);
	}
	

	@SuppressWarnings("deprecation")
	public static String format(Long time) { 
		if (time == null) {
			return "";
		} else {
			Date date = new Date(time);
			String year = "" + (date.getYear() + 1900);
			String month;
			if (date.getMonth() + 1 < 10) {
				month = "0" + (date.getMonth() + 1);
			} else {
				month = "" + (date.getMonth() + 1);
			}
			String day = "" + date.getDate();
			return day + "." + month + "." + year;
		}
	}
	
	
	public static Long stringToLong(String sDate) {
		Date date = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		try {
		    date = formatter.parse(sDate);
		    
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		Long lDate = date.getTime();
		return lDate;
	}

}
