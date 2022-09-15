package com.univ.initializer.service;


import java.util.Map;

/**
 * @author univ
 * 2022/9/05
 */
public interface TestService {

    /**
     * 多数据源：postgres
     * @param id
     * @return
     */
    Map<String, Object> multiDataSource(Long id);

    /**
     * 验证@Async
     */
    void async();

}
