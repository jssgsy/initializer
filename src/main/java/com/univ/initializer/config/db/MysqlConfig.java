package com.univ.initializer.config.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 多数据源配置：mysql连接配置
 *
 * 与{@link PostgresConfig}的区别：这里多了@Primary标签，其它注意事项参见{@link PostgresConfig}
 *
 * @author univ 2022/9/5 2:32 下午
 */
@Configuration
@MapperScan(basePackages = "com.univ.initializer.mapper.mysql", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class MysqlConfig {

	static final String MAPPER_LOCATION = "classpath:mapper/mysql/*.xml";

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "primary.mysql")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Primary
	@Bean
	public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource, @Qualifier("mysqlMybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor)
			throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		// 配置分页插件
		sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor);
		return sqlSessionFactoryBean.getObject();
	}

	@Primary
	@Bean
	public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Primary
	@Bean
	public MybatisPlusInterceptor mysqlMybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
}
