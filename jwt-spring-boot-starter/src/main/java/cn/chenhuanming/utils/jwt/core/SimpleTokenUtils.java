package cn.chenhuanming.utils.jwt.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by chenhuanming on 2017-08-09.
 *
 * @author chenhuanming
 */
public class SimpleTokenUtils implements TokenUtils {

    //Secret
    private final String SECRET;

    //long expiration of token
    private final long EXP;

    //short expiration of token
    private final long SHORT_EXP;

    //加密算法
    private final Algorithm ALGORITHM;

    public SimpleTokenUtils(String SECRET, long VALIDATE_MINUTE, long SHORT_EXP) throws UnsupportedEncodingException {
        this(SECRET, VALIDATE_MINUTE, SHORT_EXP, Algorithm.HMAC256(SECRET));
    }

    public SimpleTokenUtils(String SECRET, long VALIDATE_MINUTE, long SHORT_EXP, Algorithm ALGORITHM) {
        this.SECRET = SECRET;
        this.EXP = VALIDATE_MINUTE;
        this.SHORT_EXP = SHORT_EXP;
        this.ALGORITHM = ALGORITHM;
    }

    /**
     * 根据用户信息生成token
     *
     * @param authentication
     * @return
     */
    @Override
    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        Date now = Date.from(Instant.now());
        Date shortExpiration = Date.from(ZonedDateTime.now().plusMinutes(SHORT_EXP).toInstant());
        Date expiration = Date.from(ZonedDateTime.now().plusMinutes(EXP).toInstant());

        //create jwt
        String jwt = JWT.create()
                .withClaim("authorities", authorities)
                .withSubject(authentication.getName())
                .withIssuedAt(now)
                .withClaim("shortExp", shortExpiration)
                .withExpiresAt(expiration)
                .sign(ALGORITHM);
        return jwt;
    }

    @Override
    public String refreshToken(DecodedJWT decodedJWT) {
        Date now = Date.from(Instant.now());
        Date shortExpiration = Date.from(ZonedDateTime.now().plusMinutes(SHORT_EXP).toInstant());
        //create jwt
        String jwt = JWT.create()
                .withClaim("authorities", decodedJWT.getClaim("authorities").asString())
                .withSubject(decodedJWT.getSubject())
                .withIssuedAt(now)
                .withClaim("shortExp", shortExpiration)
                .withExpiresAt(decodedJWT.getExpiresAt())
                .sign(ALGORITHM);
        return jwt;
    }

    /**
     * 认证token有效性
     *
     * @param token
     * @return
     */
    @Override
    public boolean validateToken(String token) {
        if (token == null)
            return false;
        try {
            JWT.require(ALGORITHM).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 从token中解析中用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Authentication getAuthentication(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);
        String authorityString = decodedJWT.getClaim("authorities").asString();

        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

        if (!StringUtils.isEmpty(authorityString)) {
            authorities = Arrays.asList(authorityString.split(","))
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        }

        User principal = new User(decodedJWT.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

}
