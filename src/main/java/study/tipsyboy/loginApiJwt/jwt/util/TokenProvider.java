package study.tipsyboy.loginApiJwt.jwt.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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

    public boolean validateToken(String token) {
        try {
            // parseClaimsJws 메서드를 호출하면 기본적인 포맷을 검증하고, jwt 를 생성할 때 사용했던 secretKey 로 서명했을 때
            // 토큰에 포함된 signature 와 동일한 signature 가 생성되는지 확인합니다.
            // Header.Payload 에 대해서 동일한 secretKey 로 서명했을 때 생성된 signature 는 항상 같아야 합니다.
            // 만약 다르다면 Header.Payload의 값이 변조되었다고 판단할 수 있겠죠.
            // https://targetcoders.com/jjwt-%EC%82%AC%EC%9A%A9-%EB%B0%A9%EB%B2%95/
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(accessSecretKey))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다."); // 여기 Exception 을 잘 모르겠다.
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("토큰이 잘못되었습니다.");
        }
        return false;
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
