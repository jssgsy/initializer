package com.univ.initializer.config.shiro;

import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 手动提供ShiroFilterFactoryBean及相关组件，不要借助starter提供的实例，因为需要有较多属性要设置，集中到一起设置更易理解
 * @author univ
 * date 2025/7/7
 */
@Configuration
public class MyShiroConfigRecommend {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 自定义Filter
        Map<String, Filter> myFilters = new LinkedHashMap<>();
        myFilters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(myFilters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/shiro/home", "anon");
        filterChainDefinitionMap.put("/shiro/**", "jwt");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(realm);

        // 禁用Session，这样就不会返回Cookie了；这里用的jwt，用不着Session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        webSecurityManager.setSubjectDAO(subjectDAO);
        return webSecurityManager;
    }

    @Bean
    public Realm realm() {
        return new MyShiroRealm();
    }
}
