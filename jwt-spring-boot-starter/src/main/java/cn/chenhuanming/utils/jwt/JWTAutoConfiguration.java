package cn.chenhuanming.utils.jwt;

import cn.chenhuanming.utils.jwt.core.*;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContext;

import java.io.UnsupportedEncodingException;

/**
 * Created by chenhuanming on 2017-08-14.
 *
 * @author chenhuanming
 */
@Configuration
@ConditionalOnClass({JWT.class, SecurityContext.class})
@EnableConfigurationProperties(JWTProperties.class)
@Import(AnyRequestAuthenticatedConfiguration.class)
public class JWTAutoConfiguration {

    @Autowired
    JWTProperties jwtProperties;

    public JWTAutoConfiguration() {
        System.out.println("-----------JWT auto config-------------");
    }

    @ConditionalOnMissingBean(TokenUtils.class)
    @Bean
    TokenUtils tokenUtils(){
        try {
            return new SimpleTokenUtils(jwtProperties.getSecret(),jwtProperties.getExp(),jwtProperties.getShortExp());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("invalid jwt arguments in your config file");
    }

    @ConditionalOnMissingBean(JWTSuccessHandler.class)
    @Bean
    JWTSuccessHandler successHandler(){
        return new SimpleJWTSuccessHandler(tokenUtils());
    }

    @ConditionalOnMissingBean(TokenAuthorizationFilter.class)
    @Bean
    TokenAuthorizationFilter tokenAuthorizationFilter(){
        TokenAuthorizationFilter authorizationFilter =  new TokenAuthorizationFilter(
                jwtProperties.getAuthorizationHeaderName(),
                jwtProperties.getAuthorizationHeaderValuePrefix(),tokenUtils(),validationManager());
        authorizationFilter.setTokenExpiredHandler(expiredHandler());
        authorizationFilter.setTokenRefreshHandler(refreshHandler());
        authorizationFilter.setTokenSuccessHandler(tokenSuccessHandler());
        authorizationFilter.setTokenInvalidHandler(tokenInvalidHandler());
        return authorizationFilter;
    }

    @ConditionalOnMissingBean(JWTAuthorizationManager.class)
    @Bean
    JWTAuthorizationManager validationManager(){
        return new AssignTokenAuthorizationManager(new SignatureValidator(tokenUtils()),new ExpiredValidator(),new NeedRefreshValidator());
    }

    @ConditionalOnMissingBean(TokenExpiredHandler.class)
    @Bean
    TokenExpiredHandler expiredHandler(){
        return new RequireAuthenticationTokenHandler();
    }

    @ConditionalOnMissingBean(TokenRefreshHandler.class)
    @Bean
    TokenRefreshHandler refreshHandler(){
        return new RefreshTokenHandler(tokenUtils(),new ObjectMapper());
    }

    @ConditionalOnMissingBean(TokenSuccessHandler.class)
    @Bean
    TokenSuccessHandler tokenSuccessHandler(){
        return new DoNothingTokenSuccessHandler();
    }

    @ConditionalOnMissingBean(TokenInvalidHandler.class)
    @Bean
    TokenInvalidHandler tokenInvalidHandler(){
        return new DoNothingTokenInvalidHandler();
    }

}
