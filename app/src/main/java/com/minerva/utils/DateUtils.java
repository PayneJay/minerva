package com.minerva.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * Date转为String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String Date2Str(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String format;
        format = dateFormat.format(date);
        return format;
    }
}
