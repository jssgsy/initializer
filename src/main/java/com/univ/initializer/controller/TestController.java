package com.univ.initializer.controller;


import com.univ.initializer.entity.Demo;
import com.univ.initializer.service.DBTestService;
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
    private DBTestService dbTestService;

    @ResponseBody
    @RequestMapping("/home")
    public String test() {
        System.out.println("ok");
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/db")
    public Demo testDBConnect(Long id) {
        return dbTestService.getById(id);
    }
}
