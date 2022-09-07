package com.univ.initializer.controller;


import com.univ.initializer.service.DbTestService;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author univ
 * 2022/9/05
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private DbTestService dbTestService;

    @ResponseBody
    @RequestMapping("/home")
    public String test() {
        System.out.println("ok");
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/db")
    public Map<String, Object> testDBConnect(Long id) {
        return dbTestService.multiDataSource(id);
    }
}
