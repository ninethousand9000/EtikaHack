package me.ninethousand.fate.api.util.math;

import java.util.Calendar;

public class TimeUtil {
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

}
