package com.univ.initializer.hook;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author univ 2023/3/3 16:28
 */
public class UnivApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		System.out.println("===UnivApplicationContextInitializer=== " + applicationContext.getEnvironment().getPropertySources().size());
	}
}
