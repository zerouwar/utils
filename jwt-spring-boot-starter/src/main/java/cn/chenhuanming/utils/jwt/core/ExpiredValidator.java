package cn.chenhuanming.utils.jwt.core;

import cn.chenhuanming.utils.jwt.exception.JWTException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class ExpiredValidator implements Validator<DecodedJWT> {
    @Override
    public ValidationResult validate(DecodedJWT decodedJWT) throws JWTException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = LocalDateTime.ofInstant(decodedJWT.getExpiresAt().toInstant(), ZoneId.systemDefault());

        if(now.isAfter(expire))
            return new ValidationResult(true, JWTProperties.ValidationState.EXPIRED);
        else
            return null;
    }
}
