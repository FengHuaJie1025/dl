package com.dl.common.utils;

import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * Date相关方法
     * @return
     */
    public static Date nowDate() {
        return new Date();
    }

    public static Date addMinuteDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,minutes);
        return calendar.getTime();
    }

    public static Date todayDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,minutes);
        return calendar.getTime();
    }

    /**
     * LocalDateTime相关方法
     * @return
     */
    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now();
    }

    public static String getCurrentDateStr(String formatter) {
        SimpleDateFormat fm = new SimpleDateFormat(formatter);
        return fm.format(new Date());
    }
}
