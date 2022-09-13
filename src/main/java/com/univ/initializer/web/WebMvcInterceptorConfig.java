package com.univ.initializer.web;

import com.univ.initializer.interceptor.LogTraceInterceptor;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author univ 2022/9/13 10:24 上午
 */
@Component
public class WebMvcInterceptorConfig implements WebMvcConfigurer {

	@Resource
	private LogTraceInterceptor logTraceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logTraceInterceptor);
	}
}
