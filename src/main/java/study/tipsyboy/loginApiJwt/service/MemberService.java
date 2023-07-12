package study.tipsyboy.loginApiJwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.tipsyboy.loginApiJwt.dto.member.MemberInfoResponseDto;
import study.tipsyboy.loginApiJwt.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberInfoResponseDto findMemberInfoByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .map(Member -> MemberInfoResponseDto.from(Member))
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id=" + memberId));
    }


}
