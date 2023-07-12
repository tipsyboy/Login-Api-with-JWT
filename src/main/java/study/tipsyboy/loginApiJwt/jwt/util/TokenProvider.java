package study.tipsyboy.loginApiJwt.jwt.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 사용자 정보를 통해서 JWT 를 만들어 내거나,
 * 토큰을 복호화해서 사용자 정보를 얻는 등의 토큰의 유틸적인 부분을 담당하는 클래스
 */

@Component
public class TokenProvider {

    private final String accessSecretKey;
    private final String refreshSecretKey;

    private final static Long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
    private final static Long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    public TokenProvider(@Value("${jwt.secretKey}") String accessSecretKey,
                         @Value("${jwt.refreshKey}") String refreshSecretKey) {
        this.accessSecretKey = accessSecretKey;
        this.refreshSecretKey = refreshSecretKey;
    }

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, ACCESS_TOKEN_EXPIRE_TIME, accessSecretKey);
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, REFRESH_TOKEN_EXPIRE_TIME, refreshSecretKey);
    }

    public Authentication getAuthenticationByToken(String token) {
        Claims claims = parseToken(token);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), null, authorities);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }



    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(accessSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private String createToken(Authentication authentication,
                               Long expire, String secretKey) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority -> GrantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
