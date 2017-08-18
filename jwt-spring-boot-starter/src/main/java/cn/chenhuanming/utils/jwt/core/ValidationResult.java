package cn.chenhuanming.utils.jwt.core;

import lombok.Getter;

/**
 * Created by chenhuanming on 2017-08-17.
 *
 * @author chenhuanming
 */
@Getter
public class ValidationResult {

    private boolean doChain;
    private JWTProperties.ValidationState validationState;

    public ValidationResult(boolean doChain, JWTProperties.ValidationState validationState) {
        this.doChain = doChain;
        this.validationState = validationState;
    }
}
