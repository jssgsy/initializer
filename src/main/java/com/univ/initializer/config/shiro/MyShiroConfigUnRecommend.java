package com.univ.initializer.config.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 思路：利用starter中提供的核心组件的实例，再在这里提供需要自定义的组件，但其实行不通；
 *
 * 不推荐这样做也基本没法这样做；
 *  1. 不推荐这样做：太分散了，不易查看；
 *  2. 没法这样做：此时没法自定义Shiro范畴的Filter；
 *      自定义Filter要交由ShiroFilterFactoryBean，而ShiroFilterChainDefinition(已经包含自定义Filter)也是由其使用；
 *      因此根本没法拿到它再来设置自定义Filter(ShiroFilterChainDefinition要求使用的Filter已经存在，直接启动失败)；
 *      何况ShiroFilterFactoryBean还是一个FactoryBean，没法拿到它作定制化修改；
 *      但是又不能用@Component修饰自定义的Filter，这会使其成为一个Servlet Filter，且会拦截所有请求(即使shiro侧不做任何配置)；
 *
 * 正确的做法参见：{@link MyShiroConfigRecommend}
 * @author univ
 * date 2025/7/2
 */
// @Configuration
public class MyShiroConfigUnRecommend {

    @Bean
    public Realm realm() {
        return new MyShiroRealm();
    }

    /**
     * @see org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration#securityManager(List)
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // realm是自定义的，因此DefaultWebSecurityManager也自定义，不要starte中的
        defaultWebSecurityManager.setRealm(realm());
        return defaultWebSecurityManager;
    }

    /**
     * 禁用Session，但放这里其实不知道被谁调用了，这是分散的缺点
     * @see ShiroAutoConfiguration#sessionStorageEvaluator()
     * @return
     */
    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        return defaultSessionStorageEvaluator;
    }
    /**
     * 也可直接在ShiroFilterFactoryBean中设置，但这里想使用starter中的ShiroFilterFactoryBean，因此直接这里提供即可
     * @see org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration#shiroFilterChainDefinition()
     * ShiroFilterFactoryBean定义在{@link ShiroWebFilterConfiguration#shiroFilterFactoryBean()}
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        shiroFilterChainDefinition.addPathDefinition("/actuator/**", "anon");
        // shiroFilterChainDefinition.addPathDefinition("/test/**", "authc");
        // 要求JwtFilter被注册，过于分散，@see JwtFilter
        shiroFilterChainDefinition.addPathDefinition("/**", "jwt");
        return shiroFilterChainDefinition;
    }

    /**
     * 不要这样做，是map类型，springboot会自动收集所有的Filter
     * 自定义Filter
     * 会被注入到ShiroFilterFactoryBean中
     * @see org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration
     */
    /*@Bean
    public Map<String, Filter> filterMap() {
        HashMap<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());
        return filterMap;
    }*/


}
