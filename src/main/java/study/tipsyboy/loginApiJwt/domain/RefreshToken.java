package study.tipsyboy.loginApiJwt.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@Entity
public class RefreshToken {

    @Id
    @Column(name = "rt_key")
    private String memberName; // refreshToken 이 발급된 memberName

    private String refreshKey; // refreshToken key 값

    public RefreshToken(String memberName, String refreshKey) {
        this.memberName = memberName;
        this.refreshKey = refreshKey;
    }

    public RefreshToken updateToken(String token) {
        this.refreshKey = token;
        return this;
    }
}
