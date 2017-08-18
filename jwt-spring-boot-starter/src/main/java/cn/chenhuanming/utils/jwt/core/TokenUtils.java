package cn.chenhuanming.utils.jwt.core;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;

/**
 * Created by chenhuanming on 2017-08-15.
 *
 * @author chenhuanming
 */
public interface TokenUtils {
    String generateToken(Authentication authentication);

    String refreshToken(DecodedJWT decodedJWT);

    boolean validateToken(String token);

    Authentication getAuthentication(String token);
}
