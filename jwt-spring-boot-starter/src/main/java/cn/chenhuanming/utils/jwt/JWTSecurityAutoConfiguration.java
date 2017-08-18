package cn.chenhuanming.utils.jwt;

import cn.chenhuanming.utils.jwt.core.TokenAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by chenhuanming on 2017-08-15.
 * auto config JWT filter and disabled session and let sub-class can config other security itself
 * @author chenhuanming
 */
public class JWTSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenAuthorizationFilter tokenAuthorizationFilter;

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(tokenAuthorizationFilter,BasicAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer your-token"));
        configureHttpSecurity(http);
    }

    protected void configureHttpSecurity(HttpSecurity http) throws Exception{

    }
}
