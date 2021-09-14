package de.contracktor.model;

import java.text.DecimalFormat;

public final class CurrencyFormatter {
    private CurrencyFormatter(){}

    public static String format(Double price){
        //0 is digit, #: zero shows as absent
        DecimalFormat formatter = new DecimalFormat("###,###,###.00 €");
        return formatter.format(price);
    }
}
