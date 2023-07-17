package study.tipsyboy.loginApiJwt.domain;

import lombok.*;

import javax.persistence.*;


@Table(name = "MEMBERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true)
    private String memberName; // 유저 아이디

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roles;

    @Builder
    public Member(String memberName, String password, RoleType roles) {
        this.memberName = memberName;
        this.password = password;
        this.roles = roles;
    }
}
