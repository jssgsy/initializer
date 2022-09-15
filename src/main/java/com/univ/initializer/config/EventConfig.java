package com.univ.initializer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.AsyncTaskExecutor;

/**
 * 事件消息的配置
 * @author univ 2022/9/15 11:31 上午
 */
@Configuration
public class EventConfig {

	/**
	 * 容器默认有提供SimpleApplicationEventMulticaster，但没有设置executor，因此默认是同步消费的，这里提供一个默认为异步处理的事件广播器
	 * {@link org.springframework.context.support.AbstractApplicationContext#initApplicationEventMulticaster}
	 *
	 * @param asyncTaskExecutor
	 * @return
	 */
	@Bean
	public ApplicationEventMulticaster applicationEventMulticaster(
			AsyncTaskExecutor asyncTaskExecutor) {
		SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
		// 这样就不需要业务上的Listener使用@Async去异步处理了
		simpleApplicationEventMulticaster.setTaskExecutor(asyncTaskExecutor);
		return simpleApplicationEventMulticaster;
	}

}
