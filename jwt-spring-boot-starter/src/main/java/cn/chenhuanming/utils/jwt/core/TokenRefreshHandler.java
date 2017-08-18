package cn.chenhuanming.utils.jwt.core;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public interface TokenRefreshHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, DecodedJWT decodedJWT);
}
