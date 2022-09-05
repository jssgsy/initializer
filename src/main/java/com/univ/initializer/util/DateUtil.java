package com.univ.initializer.util;

import java.time.LocalDateTime;

/**
 * @author univ
 * 2022/8/10 1:48 下午
 */
public class DateUtil {

    /**
     * 获取某一年的开始
     * @param year
     * @return
     */
    public static LocalDateTime getStartOfYear(int year) {
        return LocalDateTime.of(year, 1, 1, 0, 0, 0);
    }
}
