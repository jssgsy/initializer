package com.univ.initializer.config;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 用来集中管理所有nacos的配置项
 *
 * @author univ 2022/12/9 16:34
 */
//@Component
/**
 * 注意，这里是以dataId与group(默认为DEFAULT_GROUP)为维度的，namespace是更全局的，是启动参数
 * autoRefreshed必须设置为true，这是同步起作用的前提；
 * type：最好也同步指定下；
 */
@NacosPropertySource(dataId = "com.univ", autoRefreshed = true, type = ConfigType.PROPERTIES)
@Data
public class NacosItemConfig {

	/**
	 * 使用@NacosValue来获取配置项name的值
	 * autoRefreshed：为true则表示配置项内容变更后这里再次读取时是否能拿到新值，一般肯定设为true。
	 *
	 * 重点：必须每个配置项都要设置autoRefreshed = true
	 * 	可全局定义nacos.config.enableRemoteSyncConfig
	 */
	@NacosValue(value = "${city:god}", autoRefreshed = true)
	private String city;

	@NacosValue(value = "${name:default_name}", autoRefreshed = true)
	private String name;

	@NacosValue(value = "${age:30}", autoRefreshed = true)
	private Integer age;


}
