package com.minerva.utils;

import com.minerva.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * Date转为String
     *
     * @param date    日期
     * @param pattern 格式
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

    /**
     * 计算某天是星期几
     *
     * @param date 日期
     * @return 0为星期日，1～6对应星期一至六
     */
    public static String dayOfWeek(Date date) {
        String week = "";
        //里面也可以直接插入date类型
        Calendar.getInstance().setTime(date);
        //计算此日期是一周中的哪一天
        int x = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (x - 1) {
            case 0:
                week = ResourceUtil.getString(R.string.week_traditional_sunday);
                break;
            case 1:
                week = ResourceUtil.getString(R.string.week_traditional_monday);
                break;
            case 2:
                week = ResourceUtil.getString(R.string.week_traditional_tuesday);
                break;
            case 3:
                week = ResourceUtil.getString(R.string.week_traditional_wednesday);
                break;
            case 4:
                week = ResourceUtil.getString(R.string.week_traditional_thursday);
                break;
            case 5:
                week = ResourceUtil.getString(R.string.week_traditional_friday);
                break;
            case 6:
                week = ResourceUtil.getString(R.string.week_traditional_saturday);
                break;
        }
        return week;
    }

    /**
     * 根据日期字符串判断当月第几周
     *
     * @param date 日期
     * @return
     */
    public static String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        String weekString = "";
        switch (week) {
            case 0:
                weekString = ResourceUtil.getString(R.string.week_traditional_sunday);
                break;
            case 1:
                weekString = ResourceUtil.getString(R.string.week_traditional_monday);
                break;
            case 2:
                weekString = ResourceUtil.getString(R.string.week_traditional_tuesday);
                break;
            case 3:
                weekString = ResourceUtil.getString(R.string.week_traditional_wednesday);
                break;
            case 4:
                weekString = ResourceUtil.getString(R.string.week_traditional_thursday);
                break;
            case 5:
                weekString = ResourceUtil.getString(R.string.week_traditional_friday);
                break;
            case 6:
                weekString = ResourceUtil.getString(R.string.week_traditional_saturday);
                break;
        }

        return weekString;
    }

    /**
     * 根据日期字符串判断当月第几周
     *
     * @param date 日期
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        // 将字符串格式化
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 第几周
        return calendar.get(Calendar.WEEK_OF_MONTH) - 1;
    }

    /**
     * 根据日期获取月份
     *
     * @param date 日期
     * @return
     */
    public static int getMonthByDate(Date date) {
        // 将字符串格式化
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 月份
        return calendar.get(Calendar.MONTH) + 1;
    }

}
