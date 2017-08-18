package cn.chenhuanming.utils.jwt.core;

import com.auth0.jwt.JWT;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenhuanming on 2017-08-08.
 *
 * @author chenhuanming
 */
@Getter
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER_NAME;

    private final String HEADER_VALUE_PREFIX;

    private final TokenUtils tokenUtils;

    private final JWTAuthorizationManager validationManager;

    @Setter
    private TokenRefreshHandler tokenRefreshHandler;

    @Setter
    private TokenExpiredHandler tokenExpiredHandler;

    @Setter
    private TokenSuccessHandler tokenSuccessHandler;

    @Setter
    private TokenInvalidHandler tokenInvalidHandler;

    public TokenAuthorizationFilter(String HEADER_NAME, String HEADER_VALUE_PREFIX, TokenUtils tokenUtils, JWTAuthorizationManager validationManager) {
        this.HEADER_NAME = HEADER_NAME;
        this.HEADER_VALUE_PREFIX = HEADER_VALUE_PREFIX;
        this.tokenUtils = tokenUtils;
        this.validationManager = validationManager;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = resolveToken((HttpServletRequest) request);

        Authentication authentication = null;

        ValidationResult result = new ValidationResult(true, JWTProperties.ValidationState.ERROR);

        if(token!=null){

            //validate token
            result = validationManager.validate(token);

            if (result.getValidationState() == JWTProperties.ValidationState.EXPIRED) {
                if (tokenExpiredHandler == null)
                    throw new IllegalStateException("tokenExpiredHandler can not be null");
                tokenExpiredHandler.handle(request, response);

            } else if (result.getValidationState() == JWTProperties.ValidationState.NEED_REFRESH) {
                if (tokenRefreshHandler == null)
                    throw new IllegalStateException("tokenRefreshHandler can not be null");
                tokenRefreshHandler.handle(request, response, JWT.decode(token));

            } else if (result.getValidationState() == JWTProperties.ValidationState.SUCCESS) {
                authentication = tokenUtils.getAuthentication(token);
                tokenSuccessHandler.handle(request, response);

            } else{
                if(tokenInvalidHandler==null)
                    throw new IllegalStateException("tokenInvalidHandler can not be null");
                tokenInvalidHandler.handle(request,response);
            }
        }

        //set authentication into security context
        if (result.isDoChain()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
            cleanAuthentication();
        }

    }

    /**
     * 从请求头解析出token
     *
     * @param request
     * @return token
     */
    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_NAME);
        if (token == null || !token.startsWith(HEADER_VALUE_PREFIX))
            return null;
        else
            return token.substring(HEADER_VALUE_PREFIX.length());
    }

    private void cleanAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
