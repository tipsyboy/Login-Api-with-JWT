package study.tipsyboy.loginApiJwt.jwt.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import study.tipsyboy.loginApiJwt.jwt.util.TokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    /**
     *  HttpRequest 요청을 받아서 Token 이 존재하는 경우(signup || login || tokenRefresh 이 아닌)
     *  즉 현재 request 로 받은 token 이 유효한 경우에 token 을 해석해서
     *  인증필터를 거쳐 인증을 완료한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        // requestHeader 에서 토큰을 찾아서 유효성 검사를 진행하고 문제가 없으면 Context 에 Authentication 인증 객체 저장
        String token = resolveToken(httpServletRequest);
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authenticationByToken = tokenProvider.getAuthenticationByToken(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationByToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String[] tokenArr = bearerToken.split(" ");
            return tokenArr[1];
        }
        return null;
    }
}
