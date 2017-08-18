package cn.chenhuanming.utils.jwt.core;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public interface JWTAuthorizationManager {
    ValidationResult validate(String token);
}
