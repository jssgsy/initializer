package com.univ.initializer.event;

/**
 * 事件壳，必须有，因为受限于spring提供的ApplicationEvent，其没有无参构造函数，导致子类也必须定义有参构造函数
 * @author univ 2022/9/15 11:07 上午
 */
public class DemoEvent extends BaseEvent<DemoEventData> {

	public DemoEvent(Object source, DemoEventData demoEventData) {
		super(source, demoEventData);
	}

}
