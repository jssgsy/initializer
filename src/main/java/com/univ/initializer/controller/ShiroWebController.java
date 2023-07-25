package com.univ.initializer.controller;

import com.univ.initializer.config.ShiroConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 如果要使用shiro的注解，则必须对之作相应的配置，否则这里的Controller甚至都不会被注册。
 * 这里注解的配置会覆盖掉{@link ShiroConfig#shiroFilterChainDefinition()}中的设置
 *
 * @author univ
 * date 2023/7/25
 */
@RestController
@RequestMapping("/shiro")
@Slf4j
public class ShiroWebController {

    @GetMapping("/anon")
    public String f1() {
        return "anon ok";
    }

    @GetMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        log.info("shiro web 登录接口通过anon filter了");
        // web环境下也可直接SecurityUtils.getSubject()获取Subject对象
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        currentUser.login(token);
        return "login ok";
    }

    /**
     * 已经有{@link ShiroConfig#shiroFilterChainDefinition()}声明了必须是登录态
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        log.info("shiro web 登录接口通过anon filter了");
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "logout ok, try another api again";
    }

    @GetMapping("/auth")
    @RequiresAuthentication
    public String auth() {
        log.info("shiro web 通过auth filter了");
        return "auth ok";
    }

    @GetMapping("/role")
    @RequiresRoles({"admin"})
    public String hasRole() {
        log.info("shiro web 通过roles filter了");
        return "hasRole ok";
    }

    @GetMapping("/role2")
    @RequiresRoles({"nope"})
    public String hasNoRole() {
        log.info("shiro web 通过roles filter了");
        return "hasNoRole ok";
    }

    @GetMapping("/permission")
    @RequiresPermissions({"user:add"})
    public String permission() {
        log.info("shiro web 通过perms filter了");
        return "permission ok";
    }


}
