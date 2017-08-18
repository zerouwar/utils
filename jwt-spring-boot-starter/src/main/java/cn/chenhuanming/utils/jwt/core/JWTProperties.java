package cn.chenhuanming.utils.jwt.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chenhuanming on 2017-08-14.
 *
 * @author chenhuanming
 */
@Getter
@Setter
@ConfigurationProperties("utils.jwt")
public class JWTProperties {

    /**
     * secret of jwt
     */
    private String secret = "utils_jwt";

    /**
     * short expiration(minute).Client will be required refreshing token
     * if token is invalid within short expiration and valid within {@code exp}
     */
    private long shortExp = 15;

    /**
     * long expiration(minute).Client must be authorized when token is invalid within exp.
     */
    private long exp = 10080;

    /**
     * header name got by {@link TokenAuthorizationFilter} to get token
     */
    private String authorizationHeaderName = "Authorization";

    /**
     * prefix of header value dealt by {@link TokenAuthorizationFilter}
     */
    private String authorizationHeaderValuePrefix = "Bearer ";

    /**
     * Created by chenhuanming on 2017-08-16.
     *
     * @author chenhuanming
     */
    public static enum ValidationState {
        SUCCESS,EXPIRED,NEED_REFRESH,ERROR;
    }


}
