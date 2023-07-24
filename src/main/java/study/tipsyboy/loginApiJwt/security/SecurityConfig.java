package study.tipsyboy.loginApiJwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import study.tipsyboy.loginApiJwt.jwt.config.JwtSecurityConfig;
import study.tipsyboy.loginApiJwt.jwt.exception.JwtAccessDeniedHandler;
import study.tipsyboy.loginApiJwt.jwt.exception.JwtAuthenticationEntryPoint;

@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 을 동작할 수 있도록 함
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtSecurityConfig jwtSecurityConfig;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico",
                        "/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                // 스프링 시큐리티의 기본 방식인 Session 방식을 사용하지 않기 위해서 Stateless 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // H2-Console 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // exception handling 을 위한 설정
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // customFilter 를 기존 시큐리티 필터보다 앞에 설정했던 jwtSecurityConfig 를 적용
                .and()
                .apply(jwtSecurityConfig)

                .and()
                .authorizeRequests()
                .mvcMatchers("/api/auth/**", "/auth/**").permitAll()
                .anyRequest().authenticated();
//                .mvcMatchers("/**").hasAnyRole("MEMBER", "ADMIN");

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
