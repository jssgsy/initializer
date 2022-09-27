package com.univ.initializer.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.univ.initializer.entity.mysql.Single;
import com.univ.initializer.entity.postgres.Demo;
import com.univ.initializer.event.DemoEvent;
import com.univ.initializer.event.DemoEventData;
import com.univ.initializer.mapper.mysql.SingleMapper;
import com.univ.initializer.mapper.postgres.DemoMapper;
import com.univ.initializer.service.TestService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author univ
 * 2022/9/05
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    /**
     * postgres库中的表
     */
//    @Resource
//    private DemoMapper demoMapper;

    /**
     * mysql库中的表
     */
    @Resource
    private SingleMapper singleMapper;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Map<String, Object> multiDataSource(Long id) {
        Map<String, Object> map = new HashMap<>();

        // 直接验证分页可行；
        /*LambdaQueryWrapper<Demo> queryWrapper = Wrappers.lambdaQuery(Demo.class);
        Page<Demo> page = new Page<>();
        page.setCurrent(2);
        page.setSize(2);
        Page<Demo> r1 = demoMapper.selectPage(page, queryWrapper);
        map.put("postgresql", r1.getRecords());*/

        Page<Single> page1 = new Page<>();
        page1.setCurrent(2);
        page1.setSize(3);
        LambdaQueryWrapper<Single> queryWrapper1 = Wrappers.lambdaQuery(Single.class);
        Page<Single> r2 = singleMapper.selectPage(page1, queryWrapper1);
        map.put("mysql", r2.getRecords());
        return map;
    }

    @Override
    @Async
    public void async() {
        log.info("@Async修饰的方法执行了，观察是否新线程，观察是否获取到TraceID");
    }

    /**
     * 这里不用@Async也能异步执行了。{@link com.univ.initializer.config.EventConfig#applicationEventMulticaster(AsyncTaskExecutor)}
     * @param event
     */
    @Override
    @EventListener(classes = DemoEvent.class)
    public void listenDemoEvent(DemoEvent event) {
        log.info("@EventListener 监听到DemoEvent事件了, event:{}", event);
    }

    /**
     * 此时如果抛出事件的方法有db事务回滚，则这里不会监听到
     * @param event
     */
    @Override
    @TransactionalEventListener
    public void transactionListenDemoEvent(DemoEvent event) {
        log.info("@TransactionalEventListener 监听到DemoEvent事件了, event:{}", event);
    }

    /**
     * 此时{@link #listenDemoEvent}会监听到消息，而{@link #transactionListenDemoEvent}不会监听到消息
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void dbThrowException() {
        Single single = singleMapper.selectById(1L);
        log.info("更新之前， {}", single);
        single.setAge(single.getAge() + 100);
        int i = singleMapper.updateById(single);
        log.info("更新db的结果: {}", i > 0);
        applicationEventPublisher.publishEvent(new DemoEvent(this, new DemoEventData()));
        throw new RuntimeException("故意抛出异常了，观察事务回滚");
    }


}
