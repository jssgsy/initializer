package com.univ.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author univ
 * 2022/9/05
 */
// mytodo：有说DataSourceTransactionManagerAutoConfiguration类也需要排除的，需要深究
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
// 多数据源的情况下，这里就不用再配置了
//@MapperScan({"com.univ.initializer.mapper"})
@EnableCaching
@EnableAsync
public class InitializerApplication {
    public static void main(String[] args) {
        SpringApplication.run(InitializerApplication.class, args);
    }
}
