package com.univ.initializer.config.db.dynamic;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author univ 2022/9/9 9:40 上午
 */
@Configuration
@MapperScan({"com.univ.initializer.mapper.dynamic"})
public class DataSourceConfig {

	/**
	 * 实际的数据源1
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "first.db")
	public DataSource dataSource1() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 实际的数据源1
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "second.db")
	public DataSource dataSource2() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 提供给spring的数据源：使用的是AbstractRoutingDataSource实例
	 * @return
	 */
	@Primary
	@Bean
	public DataSource dataSource() {
		Map<Object, Object> predefinedDataSources = new HashMap<>();
		predefinedDataSources.put(DbKeyConstant.FIRST, dataSource1());
		predefinedDataSources.put(DbKeyConstant.SECOND, dataSource2());
		return new MyDynamicDataSource(dataSource1(), predefinedDataSources);
	}

}
