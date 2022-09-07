package com.univ.initializer.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus分页插件配置
 * 在多数据源下，这里就不需要了，需要配置在各数据源的SqlSessionFactory中，参考{@link com.univ.initializer.config.db.MysqlConfig}
 * @see <a href='https://baomidou.com/pages/2976a3/#spring'>mybatis-plus插件官网说明</a>
 *
 * @author univ
 *
 */
//@Configuration
public class MybatisPlusInterceptorConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }

}
