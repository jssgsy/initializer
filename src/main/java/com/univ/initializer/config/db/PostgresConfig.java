package com.univ.initializer.config.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 多数据源配置：postgres连接配置
 *
 * 1. 关于扫描路径：
 * 	MapperScan：指定要扫描的Mapper接口
 * 	MAPPER_LOCATION：指定要扫描的mapper xml文件
 *
 * 2. 经验证，@MapperScan中不论是使用sqlSessionFactoryRef还是使用sqlSessionTemplateRef都可正常运行；
 * 3. 生成SqlSessionFactory时一定要使用mybatis-plus的MybatisSqlSessionFactoryBean，而不是原生的SqlSessionFactoryBean，可能是因为这里使用的是mybatis-plus原因；
 *
 * @author univ 2022/9/5 2:32 下午
 */
@Configuration
@MapperScan(basePackages = "com.univ.initializer.mapper.postgres", sqlSessionTemplateRef = "postgresSqlSessionTemplate")
public class PostgresConfig {

	static final String MAPPER_LOCATION = "classpath:mapper/postgres/*.xml";

	@Bean
	@ConfigurationProperties(prefix = "second.postgres")
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	public DataSourceTransactionManager postgresTransactionManager(@Qualifier("postgresDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public SqlSessionFactory postgresSqlSessionFactory(@Qualifier("postgresDataSource") DataSource dataSource, @Qualifier("postgresMybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor)
			throws Exception {
		// 可能是因为集成的是mybatis-plus，所以这里一定要使用MybatisSqlSessionFactoryBean，而不是原生的SqlSessionFactoryBean；
		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		// ！不要写成getResource。尽量copy而不是看一眼然后敲键盘
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		// 配置分页插件
		sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor);
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate postgresSqlSessionTemplate(@Qualifier("postgresSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public MybatisPlusInterceptor postgresMybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
		return interceptor;
	}
}
