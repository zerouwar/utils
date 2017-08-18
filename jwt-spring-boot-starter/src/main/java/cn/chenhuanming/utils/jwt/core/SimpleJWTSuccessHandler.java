package cn.chenhuanming.utils.jwt.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenhuanming on 2017-08-15.
 *
 * @author chenhuanming
 */
@Component
public class SimpleJWTSuccessHandler extends JWTSuccessHandler {

    public SimpleJWTSuccessHandler(TokenUtils tokenUtils) {
        super(tokenUtils,new ObjectMapper());
    }

    public Object getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        return objectMapper.createObjectNode()
                .put("token",tokenUtils.generateToken(authentication));
    }

}
