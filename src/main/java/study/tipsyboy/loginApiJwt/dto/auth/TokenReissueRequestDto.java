package study.tipsyboy.loginApiJwt.dto.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenReissueRequestDto {

    private String accessToken;
    private String refreshToken;

}
