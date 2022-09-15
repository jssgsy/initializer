package com.univ.initializer.config;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author univ 2022/9/15 8:49 上午
 */
@Configuration
public class ExecutorConfig {

	@Bean
	AsyncTaskExecutor asyncTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		// 重点1：为ThreadPoolTaskExecutor设置TaskDecorator值
		threadPoolTaskExecutor.setTaskDecorator(taskDecorator());
		return threadPoolTaskExecutor;
	}

	TaskDecorator taskDecorator() {
		return runnable -> {
			// 用来传递traceID
			Map<String, String> contextMap = MDC.getCopyOfContextMap();
			return () -> {
				try {
					MDC.setContextMap(contextMap);
					runnable.run();
				} finally {
					// 重点2：保险些。正常来说，即使是多线程环境，但只要在请求的结尾处有清空，则每次进入这里时MDC的值应该都已经在请求开头处重新设置好值了
					MDC.clear();
				}
			};
		};
	}
}
