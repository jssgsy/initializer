package com.univ.initializer.config.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * mybatis-plus支持 kingbase，因此与mysql与postgresq在使用上并无差别
 */
//@Configuration
//@MapperScan(basePackages = "com.univ.initializer.mapper.kingbase", sqlSessionTemplateRef = "kingbaseSqlSessionTemplate")
public class KingBaseConfig {
    static final String MAPPER_LOCATION = "classpath:mapper/kingbase/*.xml";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.kingbase")
    public DataSource kingbaseDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceTransactionManager kingbaseTransactionManager(@Qualifier("kingbaseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory kingbaseSqlSessionFactory(@Qualifier("kingbaseDataSource") DataSource dataSource, @Qualifier("kingbaseMybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor)
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
    public SqlSessionTemplate kingbaseSqlSessionTemplate(@Qualifier("kingbaseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public MybatisPlusInterceptor kingbaseMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.KINGBASE_ES));
        return interceptor;
    }
}
