package study.tipsyboy.loginApiJwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.tipsyboy.loginApiJwt.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberName(String memberName);
}
