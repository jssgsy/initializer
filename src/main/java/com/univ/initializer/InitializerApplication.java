package com.univ.initializer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author univ
 * 2022/9/05
 */
@SpringBootApplication
@MapperScan({"com.univ.initializer.mapper"})
@EnableCaching
public class InitializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitializerApplication.class, args);
    }

}
