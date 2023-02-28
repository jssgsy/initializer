package com.univ.initializer.controller;


import com.univ.initializer.entity.kingbase.KingbaseTest;
import com.univ.initializer.event.DemoEvent;
import com.univ.initializer.event.DemoEventData;
import com.univ.initializer.service.TestService;
import java.util.List;
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
        log.info("home ok");
        return "ok";
    }

    @ResponseBody
    @GetMapping("/db")
    public Map<String, Object> testDBConnect(Long id) {
        log.info("testDBConnect ok");
        return testService.multiDataSource(id);
    }

    @ResponseBody
    @GetMapping("/db/kingbase")
    public List<KingbaseTest> kingbase(int page, int pageSize) {
        return testService.kingbase(page, pageSize);
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

    /**
     * 普通的事务
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/event")
    public String event() {
        DemoEvent demoEvent = new DemoEvent(this, new DemoEventData());
        applicationEventPublisher.publishEvent(demoEvent);
        log.info("事件已经被发送");
        return "ok";
    }

    /**
     * 模拟db操作时抛出异常，用来测试事务
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/event/transaction")
    public String eventTransaction() {
        testService.dbThrowException();
        return "ok";
    }

//    @Resource
//    private NacosItemConfig nacosItemConfig;
//
//    @ResponseBody
//    @GetMapping("/nacos/get")
//    public Map<String, ?> nacos() {
//        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
//            put("city", nacosItemConfig.getCity());
//            put("name", nacosItemConfig.getName());
//            put("age", nacosItemConfig.getAge());
//        }};
//        System.out.println(JSONObject.toJSONString(hashMap));
//        return hashMap;
//    }


}
