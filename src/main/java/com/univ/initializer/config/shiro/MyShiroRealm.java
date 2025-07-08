package com.univ.initializer.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

import static com.univ.initializer.MybatisPlusAutoGenerator.username;

/**
 * @author univ
 * date 2025/7/3
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    private static final String VALID_JWT_TOKEN = "test";

    private static final String VALID_USERNAME = "test";

    @Override
    public boolean supports(AuthenticationToken token) {
        // 要求使用JwtToken作为登录凭证
        return token.getClass().equals(JwtToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        log.info("===MyShiroRealm#doGetAuthenticationInfo开始授权检查===primaryPrincipal:{}", primaryPrincipal);
        if (!primaryPrincipal.equals(VALID_USERNAME)) {
            return null;
        }
        Set<String> roles = new HashSet<String>() {
            {
                add("admin");
                add("provinceAdmin");
                add("cityAdmin");
                add("countyAdmin");
            }
        };
        Set<String> permissions = new HashSet<String>() {
            {
                add("user:add");
                add("user:edit");
                add("user:query");
            }
        };
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String principal = (String) jwtToken.getPrincipal();
        log.info("===MyShiroRealm#doGetAuthenticationInfo开始认证===");
        if (VALID_JWT_TOKEN.equals(principal)) {
            return new SimpleAuthenticationInfo(username, principal, "myShiroRealm");
        }
        log.error("===MyShiroRealm#doGetAuthenticationInfo认证失败===");
        // 框架内部会把doGetAuthenticationInfo中的任意异常给try住，且没有再抛出去，只能通过AuthenticationListener来接收异常信息
        return null;
    }
}
