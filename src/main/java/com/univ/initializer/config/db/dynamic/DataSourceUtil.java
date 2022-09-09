package com.univ.initializer.config.db.dynamic;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * mytodo：待优化点
 * 1. util使用@Component不太好，可再抽一层；
 *
 * @author univ 2022/9/9 4:03 下午
 */
@Component
public class DataSourceUtil {

	/**
	 * 目的：为了获取MyDynamicDataSource，进而添加数据源
	 */
	@Resource
	ApplicationContext applicationContext;

	/**
	 * 构造一个数据源
	 * @param url
	 * @param username
	 * @param password
	 * @param driverClassName
	 * @return
	 */
	public DataSource createDataSource(String url, String username, String password, String driverClassName) {
		return DataSourceBuilder.create().url(url).username(username).password(password)
				.driverClassName(driverClassName).build();
	}

	public void addDataSource(String dbKey, String url, String username, String password, String driverClassName) {
		MyDynamicDataSource bean = applicationContext.getBean(MyDynamicDataSource.class);
		bean.addDataSource(dbKey, createDataSource(url, username, password, driverClassName));
	}

}
