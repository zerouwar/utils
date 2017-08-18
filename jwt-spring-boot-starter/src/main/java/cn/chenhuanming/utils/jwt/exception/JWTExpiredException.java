package cn.chenhuanming.utils.jwt.exception;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class JWTExpiredException extends JWTException {
    private final static String message = "token is expired!";

    public JWTExpiredException() {
        super(message);
    }
}
