package com.univ.initializer.aspect;

import com.univ.initializer.config.db.dynamic.DataSourceContext;
import com.univ.initializer.config.db.dynamic.DynamicDb;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 主要用来消除切库时的模板代码：在这里统一设置和清除dbKey
 *
 * 注意：这里使用的@within：此时DynamicDb只能注解在类上
 *
 * @author univ 2022/9/9 1:48 下午
 */
@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {

	@Around("@within(com.univ.initializer.config.db.dynamic.DynamicDb)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			log.info("动态数据源：被aop拦截了");
			String dbKey = joinPoint.getTarget().getClass().getAnnotation(DynamicDb.class).value();
			DataSourceContext.setDbKey(dbKey);
			return joinPoint.proceed();
		} finally {
			DataSourceContext.remove();
		}
	}
}
