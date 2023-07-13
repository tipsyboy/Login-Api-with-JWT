package study.tipsyboy.loginApiJwt.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginResponseDto {

    private String memberName;

    private String accessToken;
    private String refreshToken;

}
