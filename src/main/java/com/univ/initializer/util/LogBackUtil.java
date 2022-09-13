package com.univ.initializer.util;

import java.util.UUID;

/**
 * @author univ
 * 2022/9/13 10:03 上午
 */
public class LogBackUtil {

    /**
	 * 获取requestId，这里简单采用uuid
     * @return
	 */
    public static String getLogId() {
        return UUID.randomUUID().toString();
    }
}
