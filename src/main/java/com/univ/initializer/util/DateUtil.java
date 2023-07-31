package com.univ.initializer.util;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 * @author univ
 * 2022/8/10 1:48 下午
 */
public class DateUtil {

    /**
     * 今年开始
     * @return 2023-01-01 00:00:00
     */
    public static LocalDateTime getStartOfCurrentYear() {
        return LocalDate.now().withMonth(1).withDayOfMonth(1).atStartOfDay();
    }

    /**
     * 今年结尾
     * @return 2023-01-01 00:00:00
     */
    public static LocalDateTime getEndOfCurrentYear() {
        return getStartOfCurrentYear().plusYears(1).minusNanos(1);
    }

    /**
     * 获取某一年的开始
     * @param year 2021
     * @return 2021-01-01T00:00
     */
    public static LocalDateTime getStartOfYear(int year) {
        return LocalDate.of(year, Month.JANUARY, 1).atStartOfDay();
    }

    /**
     * 获取某一年的结束尾
     * @param year 如2021、2022等；
     * @return 2020-12-31 23:59:59 9999999
     */
    public static LocalDateTime getEndOfYear(int year) {
        return getStartOfYear(year + 1).minusNanos(1);
    }

    /**
     * 本季度起始
     * @return 2023-01-01 00:00:00
     */
    public static LocalDateTime getStartOfCurrentQuarter() {
        LocalDate now = LocalDate.now();
        Month month = Month.of(now.getMonth().firstMonthOfQuarter().getValue());
        return LocalDateTime.of(LocalDate.of(now.getYear(), month, 1), LocalTime.MIN);
    }

    /**
     * 本季度结束
     * 没有加一个季度的方法，但其实就是加3个月
     * @return 2023-01-01 00:00:00
     */
    public static LocalDateTime getEndOfCurrentQuarter() {
        return getStartOfCurrentQuarter().plusMonths(3).minusNanos(1);
    }

    /**
     * 当月月初
     * @return 2023-05-01T00:00
     */
    public static LocalDateTime getStartOfThisMonth() {
        return LocalDate.now().withDayOfMonth(1).atStartOfDay();
    }

    /**
     * 当月月尾
     * @return 2023-05-01T00:00
     */
    public static LocalDateTime getEndOfThisMonth() {
        return getStartOfThisMonth().plusMonths(1).minusNanos(1);
    }

    /**
     * 本周开头(星期一)
     */
    public static LocalDateTime getStartOfCurrentWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
    }

    /**
     * 本周结尾(星期天)
     * 没有加一个星期的方法；不过直接加7天也可以间接达到目的
     */
    public static LocalDateTime getEndOfCurrentWeek() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1).atStartOfDay().minusNanos(1);
    }

    /**
     * 今日开头，即舍弃时分秒信息
     * @return 2023-05-06T00:00
     */
    public static LocalDateTime getStartOfToday() {
        return LocalDate.now().atStartOfDay();
    }

    /**
     * 今日末尾
     * @return
     */
    public static LocalDateTime getEndOfToday() {
        return getStartOfToday().plusDays(1).minusNanos(1);
    }

    /**
     * 获取当时小时
     * @return 2023-05-06T15:00
     */
    public static LocalDateTime getStartOfCurrentHour() {
        return LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
    }

}
