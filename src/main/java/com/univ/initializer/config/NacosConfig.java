package com.univ.initializer.config;

import org.springframework.context.annotation.Configuration;

/**
 * 注：nacos服务端需先启动
 *
 * @author univ 2022/12/8 16:11
 */
//@Configuration
public class NacosConfig {

	/**
	 * 使用NacosConfigListener来监听配置内容的变化
	 *
	 * 注意：
	 * 	1. 这里的生效范围是在dataId与group下的，只是group默认为DEFAULT_GROUP，dataId必传；
	 * 	2. newContent：是此group下的整个配置文件，而不是某个具体的配置项；
	 * 		疑问：这有什么用呢？
	 * 	3. 虽然这里变更的是整个配置文件，但某个配置项变更后@NacosValue可以读到最新的值
	 *
	 * @param newContent
	 * @throws Exception
	 */
//	@NacosConfigListener(dataId = "com.univ", timeout = 500)
//	public void onChange(String newContent) throws Exception {
//		System.out.println("nacos配置文件内容变更了，变更后为 : " + newContent);
//	}


}
