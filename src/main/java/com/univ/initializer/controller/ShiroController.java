package com.univ.initializer.controller;

import com.alibaba.fastjson.JSONObject;
import com.univ.initializer.config.shiro.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author univ
 * date 2025/7/8
 */
@RestController
@Slf4j
@RequestMapping("/shiro")
public class ShiroController {

    @ResponseBody
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @ResponseBody
    @RequestMapping("/role/success")
    @RequiresRoles("cityAdmin")
    public String roleSuccess() {
        return "cityAdmin";
    }

    @ResponseBody
    @RequestMapping("/role/fail")
    @RequiresRoles("cityAdmin2")
    public String roleFail() {
        return "无法访问";
    }

    @ResponseBody
    @RequestMapping("/perm/success")
    @RequiresPermissions("user:query")
    public String permSuccess() {
        return "user:query";
    }

    @ResponseBody
    @RequestMapping("/perm/fail")
    @RequiresPermissions("user:query2")
    public String permFail() {
        return "无法访问";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        Subject subject = SecurityUtils.getSubject();
        log.info("登录前===subject:{}", subject.isAuthenticated());
        // 校验用户名、密码，根据用户信息生成一个jwt
        subject.login(new JwtToken(loginDTO.getUsername()));
        log.info("登录后===subject:{}", subject.isAuthenticated());
        return "success";
    }

    @ResponseBody
    @RequestMapping("/user")
    public String currentUser() {
        log.info("当前用户为===subject:{}", SecurityUtils.getSubject().getPrincipal());
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    @ResponseBody
    @PostMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        log.info("退出登录前===subject:{}", JSONObject.toJSONString(subject));
        subject.logout();
        log.info("退出登录后===subject:{}", JSONObject.toJSONString(subject));
        return "success";
    }

    @ResponseBody
    @RequestMapping("/unauthorized")
    public String unauthorizedPage() {
        System.out.println("this is the shiro unauthorized page");
        return "unauthorized";
    }

}
