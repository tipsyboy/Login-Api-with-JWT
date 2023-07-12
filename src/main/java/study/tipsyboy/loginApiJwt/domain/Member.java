package study.tipsyboy.loginApiJwt.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String memberName; // 유저 아이디

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Builder
    public Member(String memberName, String password, RoleType role) {
        this.memberName = memberName;
        this.password = password;
        this.role = role;
    }
}
