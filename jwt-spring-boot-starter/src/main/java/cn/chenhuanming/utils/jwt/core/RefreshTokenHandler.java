package cn.chenhuanming.utils.jwt.core;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class RefreshTokenHandler implements TokenRefreshHandler {

    protected final TokenUtils tokenUtils;

    protected final ObjectMapper objectMapper;

    public RefreshTokenHandler(TokenUtils tokenUtils, ObjectMapper objectMapper) {
        this.tokenUtils = tokenUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, DecodedJWT decodedJWT) {
        response.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        try (OutputStream outputStream = response.getOutputStream();){
            objectMapper.writeValue(outputStream,objectMapper.createObjectNode()
                    .put("refreshToken",tokenUtils.refreshToken(decodedJWT)));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
