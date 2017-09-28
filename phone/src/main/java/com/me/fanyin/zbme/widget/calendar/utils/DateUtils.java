package com.me.fanyin.zbme.widget.calendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by yunfei on 16/11/16.
 */

public class DateUtils {

    public static final String CALENDAR_FORMAT = "yyyy-MM-dd";

    private DateUtils() {
    }

    /**
     * 将long 毫秒数转化为字符串时间
     *
     * @param time
     * @return
     */
    public static String longTime2FormatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat(CALENDAR_FORMAT);
        return format.format(new Date(time));
    }

    public static long formatTime2Long(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat(CALENDAR_FORMAT);
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
//            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    /**
     * 根据所给时间计算月份
     *
     * @param time
     * @return
     */
    public static int getCurrentMonth(long time) {

        String timeStr = longTime2FormatTime(time);
        return Integer.parseInt(timeStr.split("-")[1]);
    }

    /**
     * 根据所给时间计算是这个月的第几天
     *
     * @param time
     * @return
     */
    public static int getCurrentDay(long time) {
        String timeStr = longTime2FormatTime(time);
        int currentDay = Integer.parseInt(timeStr.split("-")[2]);
        int today = Integer.parseInt(longTime2FormatTime(System.currentTimeMillis()).split("-")[2]);
        return currentDay == today ? 0 : currentDay;
    }

    /**
     * 根据所给时间获取年份
     *
     * @param time
     * @return
     */
    public static int getCurrentYear(long time) {
        String timeStr = longTime2FormatTime(time);
        int currentYear = Integer.parseInt(timeStr.split("-")[0]);
        return currentYear;
    }


}
