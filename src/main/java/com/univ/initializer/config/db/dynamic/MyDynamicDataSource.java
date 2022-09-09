package com.univ.initializer.config.db.dynamic;

import com.google.common.base.Preconditions;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 1. 支持预定义数据源；
 * 2. 支持动态添加数据源
 * 3. 支持动态切换
 *
 * @author univ 2022/9/9 9:40 上午
 */
public class MyDynamicDataSource extends AbstractRoutingDataSource {

	private Map<Object, Object> targetDataSourcesCopy;

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContext.getDbKey();
	}

	/**
	 * @param defaultDataSource
	 * @param preDefinedDataSources
	 */
	public MyDynamicDataSource(DataSource defaultDataSource, Map<Object, Object> preDefinedDataSources) {
		Preconditions.checkNotNull(defaultDataSource, "至少有一个默认数据源");
		this.targetDataSourcesCopy = preDefinedDataSources;
		super.setDefaultTargetDataSource(defaultDataSource);
		super.setTargetDataSources(this.targetDataSourcesCopy);
	}

	public void addDataSource(Object dbKey, DataSource dataSource) {
		targetDataSourcesCopy.put(dbKey, dataSource);
		// 将整个数据源传递给父类
		super.setTargetDataSources(targetDataSourcesCopy);
		// 让父类进行处理
		super.afterPropertiesSet();
	}
}
