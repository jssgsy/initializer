package com.univ.initializer.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author univ 2022/9/15 11:07 上午
 */
public class DemoEvent extends ApplicationEvent {

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public DemoEvent(Object source) {
		super(source);
	}
}
