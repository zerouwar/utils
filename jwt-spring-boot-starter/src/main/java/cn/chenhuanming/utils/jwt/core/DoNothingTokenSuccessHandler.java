package cn.chenhuanming.utils.jwt.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class DoNothingTokenSuccessHandler implements TokenSuccessHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
    }
}
