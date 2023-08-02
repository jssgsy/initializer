package com.univ.initializer.service;


import com.univ.initializer.entity.kingbase.KingbaseTest;
import com.univ.initializer.event.DemoEvent;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;

/**
 * @author univ
 * 2022/9/05
 */
public interface TestService {

    /**
     * 验证kingbase数据库连接，加上分页
     * @param page
     * @param pageSize
     * @return
     */
    List<KingbaseTest> kingbase(int page, int pageSize);

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

    /**
     * 事件监听
     * @param event
     */
    void listenDemoEvent(DemoEvent event);

    /**
     * 验证@TransactionalEventListener的适用
     * @param event
     */
    @TransactionalEventListener
    void transactionListenDemoEvent(DemoEvent event);

    /**
     * 模拟db操作时抛出异常，用来测试事务
     */
    void dbThrowException();

}
