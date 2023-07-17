package study.tipsyboy.loginApiJwt.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.tipsyboy.loginApiJwt.dto.member.MemberInfoResponseDto;
import study.tipsyboy.loginApiJwt.service.member.MemberService;

import java.security.Principal;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberInfoResponseDto> getMyInfo(Principal principal) {
        return ResponseEntity.ok(memberService.findMemberInfoByMemberName(principal.getName()));
    }

    @GetMapping("/info/{memberName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberInfoResponseDto> getInfoOfMemberX(@PathVariable String memberName) {
        return ResponseEntity.ok(memberService.findMemberInfoByMemberName(memberName));
    }

}
