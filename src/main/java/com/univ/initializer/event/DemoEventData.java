package com.univ.initializer.event;

import lombok.Data;

/**
 * 实际用来存放事件数据的类。只是相对于事件的壳{@link DemoEvent}
 *
 * @author univ 2022/9/16 9:18 上午
 */
@Data
public class DemoEventData {
	private String name = "demo_event_data";
}
