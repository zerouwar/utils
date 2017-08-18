package cn.chenhuanming.utils.jwt.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenhuanming on 2017-08-16.
 * Manager to authorize JWT.{@code validators} must check signature first!
 *
 * @author chenhuanming
 */
public class AssignTokenAuthorizationManager implements JWTAuthorizationManager {

    /**
     * must check signature first!
     */
    private List<Validator<DecodedJWT>> validators = new ArrayList<>();

    public AssignTokenAuthorizationManager(List<Validator<DecodedJWT>> validators) {
        this.validators = validators;
    }

    public AssignTokenAuthorizationManager(Validator<DecodedJWT>... validators) {
        this.validators = Arrays.asList(validators);
    }

    @Override
    public ValidationResult validate(String token) {

        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(token);
        } catch (JWTDecodeException e) {
            return new ValidationResult(true, JWTProperties.ValidationState.ERROR);
        }

        for (Validator<DecodedJWT> validator : validators) {
            ValidationResult validationResult = validator.validate(decodedJWT);

            if(validationResult!=null)
                return validationResult;
        }
        return new ValidationResult(true, JWTProperties.ValidationState.ERROR);
    }
}
