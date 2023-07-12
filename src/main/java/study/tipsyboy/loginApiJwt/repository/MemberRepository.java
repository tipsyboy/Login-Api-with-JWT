package study.tipsyboy.loginApiJwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.tipsyboy.loginApiJwt.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberName(String memberName);
    Optional<Member> findByMemberName(String memberName);
}
