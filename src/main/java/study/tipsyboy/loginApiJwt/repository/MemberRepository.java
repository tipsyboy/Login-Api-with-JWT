package study.tipsyboy.loginApiJwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.tipsyboy.loginApiJwt.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberName(String memberName);
    Optional<Member> findByMemberName(String memberName);
}
