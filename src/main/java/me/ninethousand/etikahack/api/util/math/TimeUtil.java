package me.ninethousand.etikahack.api.util.math;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeUtil {
    public static int getHour() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
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
