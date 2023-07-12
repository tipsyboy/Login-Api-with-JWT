package study.tipsyboy.loginApiJwt.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.tipsyboy.loginApiJwt.jwt.filter.JwtFilter;
import study.tipsyboy.loginApiJwt.jwt.util.TokenProvider;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    /**
     * SpringSecurity 의 기본적인 Filter -> Manager -> Provider 구조에서
     * 토큰에 대해 검증하는 JwtFilter 가
     * UsernamePasswordAuthenticationFilter 를 통한 로그인 필터보다 먼저 동작해야 하므로
     * 동작 우선순위를 지정하는 메서드이다. 
     * 즉, 직접 만든 JwtFilter 를 SecurityFilter 보다 먼저 위치시키는 것
     */
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtFilter customJwtFilter = new JwtFilter(tokenProvider);
        builder.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
