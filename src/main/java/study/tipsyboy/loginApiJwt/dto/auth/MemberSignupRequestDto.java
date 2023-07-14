package study.tipsyboy.loginApiJwt.dto.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberSignupRequestDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String memberName; // member login id

    @NotNull
    @Size(min = 5, max = 50)
    private String password;

    public MemberSignupRequestDto(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }
}
