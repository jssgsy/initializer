package com.univ.initializer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.univ.initializer.entity.dynamic.Test;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuminglu
 * @since 2022-09-09
 */
public interface DynamicDataSourceTestService extends IService<Test> {

	/**
	 * 查询DB
	 * @param id
	 * @return
	 */
	Test queryDB(Long id);

}
