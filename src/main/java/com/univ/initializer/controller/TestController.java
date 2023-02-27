package com.univ.initializer.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.xxpt.gateway.shared.api.request.OapiGetJsapiTokenJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.request.OapiGettokenJsonRequest;
import com.alibaba.xxpt.gateway.shared.api.response.OapiGetJsapiTokenJsonResponse;
import com.alibaba.xxpt.gateway.shared.api.response.OapiGettokenJsonResponse;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.IntelligentGetClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import com.univ.initializer.config.NacosItemConfig;
import com.univ.initializer.entity.kingbase.KingbaseTest;
import com.univ.initializer.event.DemoEvent;
import com.univ.initializer.event.DemoEventData;
import com.univ.initializer.service.TestService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    static String appKey = "canteen_h5-o0eX6k2YfLLN0n226fa";
    static String appSecret = "G62T44152Y5pKdF60Gk6dUFx4TI6ZS57GqRxApL4";
    static String domainName = "openplatform.dg-work.cn";

    private static ExecutableClient executableClient;

    static {
        executableClient = ExecutableClient.getInstance();
        //DomainName不同环境对应不同域名，示例为sass域名
        executableClient.setDomainName(domainName);
        executableClient.setProtocal("https");
        //应用App Key
        executableClient.setAccessKey(appKey);
        //应用App Secret
        executableClient.setSecretKey(appSecret);
        executableClient.init();
    }

    /**
     * href='https://openplatform-portal.dg-work.cn/portal/?spm=a2q2b.13441934.0.0.4cba6fbaZpC5fQ#/helpdoc?apiType=serverapi&docKey=2674862'
     */
    @ResponseBody
    @GetMapping("/gettoken")
    public JSONObject gettoken() {
        //executableClient保证单例
        IntelligentGetClient intelligentGetClient = executableClient.newIntelligentGetClient("/gettoken.json");
        OapiGettokenJsonRequest oapiGettokenJsonRequest = new OapiGettokenJsonRequest();
        //应用的唯一标识key
         oapiGettokenJsonRequest.setAppkey(appKey);
        //应用的密钥
         oapiGettokenJsonRequest.setAppsecret(appSecret);
        //获取结果
        OapiGettokenJsonResponse apiResult = intelligentGetClient.get(oapiGettokenJsonRequest);

        System.out.println(apiResult.getContent().getData());
        return JSONObject.parseObject(apiResult.getContent().getData());
    }


    /**
     * https://openplatform-portal.dg-work.cn/portal/?spm=a2q2b.13441934.0.0.4cba6fbaZpC5fQ#/helpdoc?apiType=serverapi&docKey=2674861
     * @return
     */
    @ResponseBody
    @GetMapping("/get_jsapi_token")
    public JSONObject get_jsapi_token(@RequestParam("accessToken") String accessToken) {
        //executableClient保证单例
        IntelligentGetClient intelligentGetClient = executableClient.newIntelligentGetClient("/get_jsapi_token.json");
        OapiGetJsapiTokenJsonRequest oapiGetJsapiTokenJsonRequest = new OapiGetJsapiTokenJsonRequest();
        //null
        oapiGetJsapiTokenJsonRequest.setAccessToken(accessToken);
        //获取结果
        OapiGetJsapiTokenJsonResponse apiResult = intelligentGetClient.get(oapiGetJsapiTokenJsonRequest);
        System.out.println("apiResult: " + apiResult);
        return JSONObject.parseObject(apiResult.getContent().getData());
    }



}
