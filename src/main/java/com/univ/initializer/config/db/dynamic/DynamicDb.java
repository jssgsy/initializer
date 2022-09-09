package com.univ.initializer.config.db.dynamic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源切换的注解
 * 简单起见，也方便aop解析，这里只定义成注释在类型上
 *
 * @author univ 2022/9/9 1:55 下午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicDb {

	String value() default "first";
}
