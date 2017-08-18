package cn.chenhuanming.utils.jwt.core;

import cn.chenhuanming.utils.jwt.exception.JWTException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class SignatureValidator implements Validator<DecodedJWT> {

    private TokenUtils tokenUtils;

    public SignatureValidator(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public ValidationResult validate(DecodedJWT decodedJWT) throws JWTException {
        if(!tokenUtils.validateToken(decodedJWT.getToken()))
            return new ValidationResult(true, JWTProperties.ValidationState.ERROR);
        else
            return null;
    }
}
