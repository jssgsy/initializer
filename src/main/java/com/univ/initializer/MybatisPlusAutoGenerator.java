package com.univ.initializer;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.querys.KingbaseESQuery;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.util.Collections;

/**
 * 一般只需要修改配置项：addInclude
 *
 * @author liuminglu
 * 2022/8/9 2:56 下午
 */
public class MybatisPlusAutoGenerator {

    public static String url = "jdbc:mysql://localhost:3306/mybatis?useAffectedRows=true";
    public static String username = "test";
    public static String password = "123";

  public static void main(String[] args) {

        // 工程目录
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(url, username, password)
                // 1. globalConfig
                .globalConfig(builder -> {
                    builder.author("liuminglu") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java");
                })
                // 2. packageConfig
                .packageConfig(builder -> {
                    builder.parent("com.univ.initializer")
                            .entity("entity.mysql")
                            .mapper("mapper.mysql")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper/mysql"));
                })
                // 3. strategyConfig
                .strategyConfig(builder -> {
                     builder.addInclude("single");// 设置需要生成的表名
                            // .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                    // 默认生成的service接口前会加I，这样设置就不会加了
                    builder.entityBuilder().enableLombok();
                    builder.serviceBuilder().formatServiceFileName("%sService");
                })
                // 4. templateEngine
                .templateEngine(new VelocityTemplateEngine())   // 使用Velocity引擎模板，默认的是Velocity引擎模板、不同的引擎要引入不同的包
                // 5. templateConfig
                // 为空表示不生成controller,其它的如service，mapper等一样
                .templateConfig(builder -> builder.controller(""))
                .templateConfig(builder -> builder.service(""))
                .templateConfig(builder -> builder.serviceImpl(""))
                .execute();
    }
}
