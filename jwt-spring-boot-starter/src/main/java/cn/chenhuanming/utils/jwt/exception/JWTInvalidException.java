package cn.chenhuanming.utils.jwt.exception;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class JWTInvalidException extends JWTException {
    private final static String message = "token is invalid!";

    public JWTInvalidException() {
        super(message);
    }
}
