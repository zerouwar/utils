package cn.chenhuanming.utils.jwt.core;

import cn.chenhuanming.utils.jwt.exception.JWTException;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public interface Validator<T> {
    ValidationResult validate(T t) throws JWTException;
}
