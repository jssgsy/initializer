package com.univ.initializer.interceptor;

import com.univ.initializer.util.LogBackUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来实现logback的调用链traceId
 *
 * @author univ 2022/9/13 10:26 上午
 */
@Component
public class LogTraceInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 和配置文件中取值时的key保持一致：%X{traceId}
	 */
	private static String TRACE_ID = "traceId";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		// 开头处设值
		MDC.put(TRACE_ID, LogBackUtil.getLogId());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		// 请求结束处移除
		MDC.remove(TRACE_ID);
		super.afterCompletion(request, response, handler, ex);
	}
}
