package cn.chenhuanming.utils.jwt.exception;

/**
 * Created by chenhuanming on 2017-08-16.
 *
 * @author chenhuanming
 */
public class JWTNeedRefreshException extends JWTException {
    private final static String message = "token needs to be refreshed!";

    public JWTNeedRefreshException() {
        super(message);
    }
}
