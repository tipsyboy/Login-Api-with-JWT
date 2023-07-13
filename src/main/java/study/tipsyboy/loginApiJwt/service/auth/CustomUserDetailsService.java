package study.tipsyboy.loginApiJwt.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.tipsyboy.loginApiJwt.domain.Member;
import study.tipsyboy.loginApiJwt.repository.MemberRepository;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. memberName=" + memberName));

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().getKey());
        return new User(memberName, member.getPassword(), Collections.singleton(grantedAuthority));
    }
}
