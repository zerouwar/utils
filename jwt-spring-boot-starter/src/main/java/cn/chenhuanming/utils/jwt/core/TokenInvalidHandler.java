package cn.chenhuanming.utils.jwt.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhuanming on 2017-08-17.
 *
 * @author chenhuanming
 */
public interface TokenInvalidHandler {
    void handle(HttpServletRequest request, HttpServletResponse response);
}
