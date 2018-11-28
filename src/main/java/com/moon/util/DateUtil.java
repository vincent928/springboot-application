package com.moon.util;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Author : moon
 * Date  : 2018/11/27 15:18
 * Description : Class for 日期工具类
 */
public class DateUtil {


    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_B = "yyyyMMdd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_PATTERN_B = "yyyyMMddHHmm";
    public static final String DATE_TIME_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_SECOND_B = "yyyyMMddHHmmss";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String TIME_PATTERN_B = "HHmm";
    public static final String TIME_PATTERN_SECOND = "HH:mm:ss";
    public static final String TIME_PATTERN_SECOND_B = "HHmmss";


    /**
     * 获取当前日期
     *
     * @return 2018-11-27
     */
    public static String getDate() {
        return LocalDate.now().toString();
    }


    /**
     * 获取年份
     *
     * @return 2018
     */
    public static int getYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取月份
     *
     * @return 11
     */
    public static int getMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 获取天数(月份)
     *
     * @return 27
     */
    public static int getDayOfMonth() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * 获取星期
     *
     * @return 2
     */
    public static int getWeek() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

    /**
     * 获取天数(年份)
     *
     * @return
     */
    public static int getDayOfYear() {
        return LocalDate.now().getDayOfYear();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN_SECOND));
    }

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getTime(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前 小时(24小时制)
     *
     * @return
     */
    public static int getHour() {
        return LocalTime.now().getHour();
    }

    /**
     * 获取当前 分钟
     *
     * @return
     */
    public static int getMinute() {
        return LocalTime.now().getMinute();
    }

    /**
     * 获取当前 秒
     *
     * @return
     */
    public static int getSecond() {
        return LocalTime.now().getSecond();
    }


    /**
     * 获取当前日期时间
     *
     * @return 2018-11-27 15:32:51
     */
    public static String getDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_SECOND));
    }

    /**
     * 获取当前日期时间
     *
     * @param pattern 指定格式
     * @return
     */
    public static String getDateTime(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 当前日期增加 day天数
     *
     * @param day
     * @return
     */
    public static String plusDays(long day) {
        return LocalDateTime.now().plusDays(day).format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_SECOND));
    }

    /**
     * 当前日期增加 day天数
     *
     * @param day
     * @param pattern
     * @return
     */
    public static String plusDays(long day, String pattern) {
        return LocalDateTime.now().plusDays(day).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 指定日期增加day天
     *
     * @param day
     * @param pattern
     * @param datetime
     * @return
     */
    public static String plusDays(long day, String pattern, String datetime) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        switch (pattern) {
            case DATE_PATTERN:
            case DATE_PATTERN_B:
                return LocalDate.parse(datetime, formatter).plusDays(day).format(formatter);
            case DATE_TIME_PATTERN:
            case DATE_TIME_PATTERN_B:
            case DATE_TIME_PATTERN_SECOND:
            case DATE_TIME_PATTERN_SECOND_B:
                return LocalDateTime.parse(datetime, formatter).plusDays(day).format(formatter);
            default:
                throw new Exception(MessageFormat.format("DateUtil.plusDays(long day,String pattern,String datetime)" +
                                "\n\t\tpattern支持[\"{0}\",\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\"]," +
                                "请检查pattern格式:{6}", DATE_PATTERN, DATE_PATTERN_B, DATE_TIME_PATTERN,
                        DATE_TIME_PATTERN_B, DATE_TIME_PATTERN_SECOND, DATE_TIME_PATTERN_SECOND_B, pattern));
        }
    }

    /**
     * 当前日期时间 减去day天
     *
     * @param day
     * @return
     */
    public static String minusDays(long day) {
        return LocalDateTime.now().minusDays(day).format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_SECOND));
    }

    /**
     * @param day
     * @param pattern
     * @return
     */
    public static String minusDays(long day, String pattern) {
        return LocalDateTime.now().minusDays(day).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @param day
     * @param pattern
     * @param datetime
     * @return
     */
    public static String minusDays(long day, String pattern, String datetime) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        switch (pattern) {
            case DATE_PATTERN:
            case DATE_PATTERN_B:
                return LocalDate.parse(datetime, formatter).minusDays(day).format(formatter);
            case DATE_TIME_PATTERN:
            case DATE_TIME_PATTERN_B:
            case DATE_TIME_PATTERN_SECOND:
            case DATE_TIME_PATTERN_SECOND_B:
                return LocalDateTime.parse(datetime, formatter).minusDays(day).format(formatter);
            default:
                throw new Exception(MessageFormat.format("DateUtil.minusDays(long day,String pattern,String datetime)" +
                                "\n\t\tpattern支持[\"{0}\",\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\"]," +
                                "请检查pattern格式:{6}", DATE_PATTERN, DATE_PATTERN_B, DATE_TIME_PATTERN,
                        DATE_TIME_PATTERN_B, DATE_TIME_PATTERN_SECOND, DATE_TIME_PATTERN_SECOND_B, pattern));
        }
    }

    //TODO:加减时间hour和minute

}
