package com.omar.bank.util;

import java.text.DecimalFormat;

public class NumberFormatter {
    public static String customFormatter(double amount) {
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(amount);
    }
}
