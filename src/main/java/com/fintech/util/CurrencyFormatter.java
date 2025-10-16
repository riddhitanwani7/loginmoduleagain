package com.fintech.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

    public static String format(double value, String currency) {
        String curr = currency == null ? "INR" : currency.toUpperCase();
        int decimals = switch (curr) {
            case "JPY" -> 0;
            case "KWD" -> 3;
            default -> 2; // INR and others
        };
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        nf.setMinimumFractionDigits(decimals);
        nf.setMaximumFractionDigits(decimals);
        try {
            return nf.format(value).replace("$", getSymbol(curr));
        } catch (Exception e) {
            return String.format(Locale.US, "%s %." + decimals + "f", curr, value);
        }
    }

    private static String getSymbol(String currency) {
        return switch (currency) {
            case "INR" -> "₹";
            case "JPY" -> "¥";
            case "KWD" -> "KWD ";
            default -> currency + " ";
        };
    }
}
