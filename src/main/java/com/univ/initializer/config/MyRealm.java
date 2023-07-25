package com.univ.initializer.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author univ
 * date 2023/7/25
 */
public class MyRealm extends AuthorizingRealm {

    // 每次授权时会调用(当然，实际一般会启用缓存)
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 取用户的标识，可能是用户名、用户id等等
        String userId = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 2. 模拟获取用户的所有角色
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        info.setRoles(roles);
        // 3. 模拟获取用户的所有权限
        Set<String> permissions = new HashSet<>();
        permissions.add("user:add");
        info.setStringPermissions(permissions);
        return info;
    }

    // 每次登录时调用
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof UsernamePasswordToken)) {
            System.out.println("AuthenticationToken类型不对");
            return null;
        }
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String username = token1.getUsername();
        char[] password = token1.getPassword();
        if (username.equals("univ") && new String(password).equals("1234")) {
            return new SimpleAuthenticationInfo("univ", "1234", getName());
        }
        return null;
    }
}
