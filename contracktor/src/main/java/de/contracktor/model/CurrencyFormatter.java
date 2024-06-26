package de.contracktor.model;

import java.text.DecimalFormat;

public final class CurrencyFormatter {
    private CurrencyFormatter(){}

    public static String format(Double price){
        //0 is digit, #: zero shows as absent
    	if(price == null) {
    		return "No Price";
    	}
        DecimalFormat formatter = new DecimalFormat("###,###,###,##0.00 €");
        return formatter.format(price);
    }
}
