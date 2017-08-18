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
public class NeedRefreshValidator implements Validator<DecodedJWT> {

    private final String SHORT_EXP = "shortExp";

    @Override
    public ValidationResult validate(DecodedJWT decodedJWT) throws JWTException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime shortExp = LocalDateTime.ofInstant(decodedJWT.getClaim(SHORT_EXP).asDate().toInstant(), ZoneId.systemDefault());

        if(now.isAfter(shortExp))
            return new ValidationResult(false, JWTProperties.ValidationState.NEED_REFRESH);
        else
            return null;
    }
}
