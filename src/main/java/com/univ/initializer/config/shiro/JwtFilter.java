package com.univ.initializer.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author univ
 * date 2025/7/7
 */
@Slf4j
/**
 * 此时会由spring自动收集到 {@link org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration#filterMap}
 * 千万不要使用@Component修饰，因为这会使得JwtFilter成为一个Servlet范畴的Filter，且始终都会拦截所有请求(不论在shiro中是如何配置的)
 *  即其不仅成为了一个Shiro范畴的Filter，还成为了Servlet范畴的Filter；
 *  即使没有在shiro中配置适用于/**，其也会拦截所有请求，即所有请求均无法得到通过
 */
// @Component("jwt")
public class JwtFilter extends AuthenticatingFilter {
    private static final String JWT_TOKEN = "X-ACCESS-TOKEN";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        log.info("===执行JwtFilter#createToken===");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwtToken = httpServletRequest.getHeader(JWT_TOKEN);
        jwtToken = StringUtils.isNotEmpty(jwtToken) ? jwtToken : httpServletRequest.getParameter(JWT_TOKEN);
        return new JwtToken(jwtToken);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("===执行JwtFilter#onAccessDenied===");
        // 直接使用默认的Subject重建逻辑
        return executeLogin(request, response);
    }
}
