package com.univ.initializer.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * jdk提供的事件基类EventObject没有默认构造函数，因此所有自定义的事件类都需要提供带source的构造函数，比较麻烦(因为不是业务数据)。
 * 但事已至此，只能考虑如何减少事件类的模板代码(构造函数)，因此有了此类，这样事件类只需：
 * 1. 定义一个构造函数；
 * 2. 定义一个用来承载事件数据的类；
 * 即，其实对于每个事件，都需要两个类，一个是壳，如{@link DemoEvent}，一个是用来承载事件数据的，如{@link DemoEventData}
 *
 * 好处：事件壳定义好了就不用变了，新增或修改字段直接在事件数据类中操作即可；
 *
 * 注：
 * 1. 这里加上@ToString，则打印日志时不用额外转成json了
 * 2. EventObject中source字段由transient修饰，是没法序列化的
 *
 * @author univ 2022/9/16 9:12 上午
 */
@Getter
@ToString
public class BaseEvent<T> extends ApplicationEvent {

	private T eventData;

	public BaseEvent(Object source, T eventData) {
		super(source);
		this.eventData = eventData;
	}

}
