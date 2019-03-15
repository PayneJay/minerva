package com.minerva.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * Date转为String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String date2Str(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String format;
        format = dateFormat.format(date);
        return format;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param time
     * @return true今天 false不是
     */
    public static boolean isToday(long time) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            return diffDay == 0;
        }
        return false;
    }
}
