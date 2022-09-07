package com.univ.initializer.service;


import com.univ.initializer.entity.mysql.Single;
import com.univ.initializer.entity.postgres.Demo;
import java.util.Map;

/**
 * @author univ
 * 2022/9/05
 */
public interface DbTestService {

    /**
     * 多数据源：postgres
     * @param id
     * @return
     */
    Map<String, Object> multiDataSource(Long id);

}
