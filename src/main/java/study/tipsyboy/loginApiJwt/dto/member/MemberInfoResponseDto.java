package study.tipsyboy.loginApiJwt.dto.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tipsyboy.loginApiJwt.domain.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberInfoResponseDto {

    private Long memberId;
    private String memberName;


    private MemberInfoResponseDto(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public static MemberInfoResponseDto from(Member entity) {
        return new MemberInfoResponseDto(entity.getMemberId(), entity.getMemberName());
    }
}
