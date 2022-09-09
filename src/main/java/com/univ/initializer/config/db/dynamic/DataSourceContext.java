package com.univ.initializer.config.db.dynamic;

import org.springframework.stereotype.Component;

/**
 * 数据源上下文：可在此获取依据什么来动态切换数据源；
 *
 * @author univ 2022/9/9 1:45 下午
 */
@Component
public class DataSourceContext {

	/**
	 * 保存在本地线程，同时每次使用前先set，使用后remove
	 */
	private static ThreadLocal<String> dbKey = new ThreadLocal<>();

	public static void setDbKey(String key) {
		dbKey.set(key);
	}

	public static String getDbKey() {
		return dbKey.get();
	}

	public static void remove() {
		dbKey.remove();;
	}
}
