package cn.chenhuanming.utils.jwt;

import cn.chenhuanming.utils.jwt.core.JWTSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Created by chenhuanming on 2017-08-17.
 *
 * @author chenhuanming
 */
@Configuration
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
public class AnyRequestAuthenticatedConfiguration extends JWTSecurityAutoConfiguration {

    @Autowired(required = false)
    UserDetailsService userDetailsService;

    @Autowired
    JWTSuccessHandler successHandler;

    @Autowired(required = false)
    AuthenticationFailureHandler failureHandler;

    public AnyRequestAuthenticatedConfiguration() {
        System.out.println("--------AnyRequestAuthenticatedConfiguration is active!");
    }

    @Override
    protected void configureHttpSecurity(HttpSecurity http) throws Exception {

        http.antMatcher("/**")
                .authorizeRequests().antMatchers("/**").authenticated()
            .and().csrf().disable();

        if(userDetailsService!=null)
            http.userDetailsService(userDetailsService);

        FormLoginConfigurer<HttpSecurity> formLoginConfigurer = http.formLogin().successHandler(successHandler);
        if(failureHandler!=null)
            formLoginConfigurer.failureHandler(failureHandler);
    }
}
