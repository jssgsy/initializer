package com.univ.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author univ
 * 2022/9/05
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
// 多数据源的情况下，这里就不用再配置了
//@MapperScan({"com.univ.initializer.mapper"})
@EnableCaching
public class InitializerApplication {
    public static void main(String[] args) {
        SpringApplication.run(InitializerApplication.class, args);
    }
}
