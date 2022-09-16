package com.univ.initializer.controller;


import com.univ.initializer.event.DemoEvent;
import com.univ.initializer.event.DemoEventData;
import com.univ.initializer.service.TestService;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author univ
 * 2022/9/05
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private TestService testService;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @ResponseBody
    @GetMapping("/home")
    public String test() {
        System.out.println("ok");
        return "ok";
    }

    @ResponseBody
    @GetMapping("/db")
    public Map<String, Object> testDBConnect(Long id) {
        log.info("xxx");
        return testService.multiDataSource(id);
    }

    @Resource
    private AsyncTaskExecutor asyncTaskExecutor;

    /**
     * logback调用链验证
     * @return
     */
    @ResponseBody
    @GetMapping("/log")
    public String log() {
        log.info("first line");

        // asyncTaskExecutor支持
        asyncTaskExecutor.submit(() -> {
            log.info("sub thread log");
        });

        // @Async支持
        testService.async();
        return "ok";
    }

    @ResponseBody
    @GetMapping("/event")
    public String event() {
        DemoEvent demoEvent = new DemoEvent(this, new DemoEventData());
        applicationEventPublisher.publishEvent(demoEvent);
        log.info("事件已经被发送");
        return "ok";
    }

}
