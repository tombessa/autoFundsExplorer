package com.github.tombessa.autofundsexplorer.util;


import java.math.BigDecimal;

public class NumberUtil {

    public static Double arrendondar(Double doubleNum) {
        BigDecimal bg;
        if (doubleNum != null) {
            if (doubleNum > 0) {
                bg = new BigDecimal(String.valueOf(doubleNum)).setScale(2, BigDecimal.ROUND_FLOOR);
            } else {
                bg = new BigDecimal(String.valueOf(doubleNum)).setScale(2, BigDecimal.ROUND_CEILING);
            }
            return bg.doubleValue();
        }
        return null;
    }
}
