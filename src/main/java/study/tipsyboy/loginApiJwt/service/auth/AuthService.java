package study.tipsyboy.loginApiJwt.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.loginApiJwt.domain.Member;
import study.tipsyboy.loginApiJwt.domain.RoleType;
import study.tipsyboy.loginApiJwt.dto.auth.MemberSignupRequestDto;
import study.tipsyboy.loginApiJwt.repository.MemberRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signup(MemberSignupRequestDto requestDto) {
        if (memberRepository.existsByMemberName(requestDto.getMemberName())) {
            throw new RuntimeException("이미 등록 되어있는 아이디 입니다. memberName=" + requestDto.getMemberName());
        }

        Member newMember = Member.builder()
                .memberName(requestDto.getMemberName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(RoleType.MEMBER)
                .build();

        memberRepository.save(newMember); // 컨텍스트 안의 동일성 보장 ?
        return newMember.getMemberId();
    }


}
