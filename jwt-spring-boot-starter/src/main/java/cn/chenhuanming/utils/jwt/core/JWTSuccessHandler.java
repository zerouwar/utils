package cn.chenhuanming.utils.jwt.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by chenhuanming on 2017-07-13.
 *
 * @author chenhuanming
 */
public abstract class JWTSuccessHandler implements AuthenticationSuccessHandler{

    protected final TokenUtils tokenUtils;

    protected final ObjectMapper objectMapper;

    public JWTSuccessHandler(TokenUtils tokenUtils, ObjectMapper objectMapper) {
        this.tokenUtils = tokenUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try (OutputStream outputStream = response.getOutputStream()){
           objectMapper.writeValue(outputStream,getResult(request, response, authentication));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    abstract public Object getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}
