package me.ninethousand.etikahack.api.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {
    public static double roundNumber(double number, int scale) {
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }

    public static double roundAvoid(double number, int places) {
        double scale = Math.pow(10, places);
        return Math.round(number * scale) / scale;
    }

    public static int clamp(int num, int min, int max) {
        return num < min ? min : Math.min(num, max);
    }
}
