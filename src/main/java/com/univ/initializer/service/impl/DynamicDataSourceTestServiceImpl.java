package com.univ.initializer.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.univ.initializer.config.db.dynamic.DataSourceContext;
import com.univ.initializer.config.db.dynamic.DataSourceUtil;
import com.univ.initializer.config.db.dynamic.DbKeyConstant;
import com.univ.initializer.config.db.dynamic.DynamicDb;
import com.univ.initializer.entity.dynamic.Test;
import com.univ.initializer.mapper.dynamic.TestMapper;
import com.univ.initializer.service.DynamicDataSourceTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuminglu
 * @since 2022-09-09
 */
@Service
@Slf4j
@DynamicDb(DbKeyConstant.THIRD)
public class DynamicDataSourceTestServiceImpl extends ServiceImpl<TestMapper, Test> implements DynamicDataSourceTestService {

	@Override
	public Test queryDB(Long id) {
		log.info("##########queryDB########");
		return getOne(Wrappers.lambdaQuery(Test.class).eq(Test::getId, id).last("limit 1"));
	}

}
