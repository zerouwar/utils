package cn.chenhuanming.utils.jwt.core;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class RequireAuthenticationTokenHandler implements TokenExpiredHandler {

    private String headerValue = "Bearer your-json-token";

    public RequireAuthenticationTokenHandler() {
    }

    public RequireAuthenticationTokenHandler(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("WWW-Authenticate",headerValue);
    }
}
