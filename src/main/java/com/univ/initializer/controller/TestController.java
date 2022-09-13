package com.univ.initializer.controller;


import com.univ.initializer.service.DbTestService;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    private DbTestService dbTestService;

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
        return dbTestService.multiDataSource(id);
    }

    /**
     * logback调用链验证
     * @return
     */
    @ResponseBody
    @GetMapping("/log")
    public String log() {
        log.info("first line");
        log.info("first line");
        log.info("third line");
        return "ok";
    }

}
