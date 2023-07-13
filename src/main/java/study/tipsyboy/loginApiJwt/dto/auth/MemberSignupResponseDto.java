package study.tipsyboy.loginApiJwt.dto.auth;

import lombok.Getter;


@Getter
public class MemberSignupResponseDto {

    private Long memberId;

    public MemberSignupResponseDto(Long memberId) {
        this.memberId = memberId;
    }
}
