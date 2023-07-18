package study.tipsyboy.loginApiJwt.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.loginApiJwt.domain.Member;
import study.tipsyboy.loginApiJwt.domain.RefreshToken;
import study.tipsyboy.loginApiJwt.domain.RoleType;
import study.tipsyboy.loginApiJwt.dto.auth.MemberLoginRequestDto;
import study.tipsyboy.loginApiJwt.dto.auth.MemberLoginResponseDto;
import study.tipsyboy.loginApiJwt.dto.auth.MemberSignupRequestDto;
import study.tipsyboy.loginApiJwt.dto.auth.TokenReissueRequestDto;
import study.tipsyboy.loginApiJwt.jwt.util.TokenProvider;
import study.tipsyboy.loginApiJwt.repository.MemberRepository;
import study.tipsyboy.loginApiJwt.repository.RefreshTokenRepository;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

//    private final AuthenticationManager authenticationManager; // 밑에 Builder 를 사용하는 것과 차이점이 뭐지..?
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public Long signup(MemberSignupRequestDto requestDto) {
        if (memberRepository.existsByMemberName(requestDto.getMemberName())) {
            throw new RuntimeException("이미 등록 되어있는 아이디 입니다. memberName=" + requestDto.getMemberName());
        }

        Member newMember = Member.builder()
                .memberName(requestDto.getMemberName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(RoleType.MEMBER)
                .build();

        memberRepository.save(newMember); // 컨텍스트 안의 동일성 보장 ?
        return newMember.getMemberId();
    }

    @Transactional
    public MemberLoginResponseDto login(MemberLoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(requestDto.getMemberName(), requestDto.getPassword());

        Authentication authentication
                = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        RefreshToken savedRefreshToken = RefreshToken.builder()
                .memberName(requestDto.getMemberName())
                .refreshKey(refreshToken)
                .build();
        refreshTokenRepository.save(savedRefreshToken);

        return MemberLoginResponseDto.builder()
                .memberName(requestDto.getMemberName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public MemberLoginResponseDto reissueToken(TokenReissueRequestDto requestDto) {

        if (!tokenProvider.validateToken(requestDto.getRefreshToken())) {
            throw new RuntimeException("유효하지 않은 Refresh Token 입니다.");
        }

        Authentication authenticationByToken = tokenProvider.getAuthenticationByToken(requestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByMemberName(authenticationByToken.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그아웃된 사용자입니다. memberName=" + authenticationByToken.getName()));

        if (!refreshToken.getRefreshKey().equals(requestDto.getRefreshToken())) {
            throw new RuntimeException("Token 정보가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(authenticationByToken);
        String reissuedRefreshToken = tokenProvider.createRefreshToken(authenticationByToken);
        refreshTokenRepository.save(refreshToken.updateToken(reissuedRefreshToken));

        return MemberLoginResponseDto.builder()
                .memberName(authenticationByToken.getName())
                .accessToken(accessToken)
                .refreshToken(reissuedRefreshToken)
                .build();
    }
}
