package com.univ.initializer.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author univ
 * date 2025/7/7
 */
@Data
public class JwtToken implements AuthenticationToken {

    private String jwtToken;

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
