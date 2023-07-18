package study.tipsyboy.loginApiJwt.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.loginApiJwt.dto.auth.*;
import study.tipsyboy.loginApiJwt.service.auth.AuthService;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignupResponseDto> signup(@Valid @RequestBody MemberSignupRequestDto requestDto) {
        Long memberId = authService.signup(requestDto);
        return ResponseEntity.ok(new MemberSignupResponseDto(memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> login(@Valid @RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto memberLoginResponseDto = authService.login(requestDto);
        return ResponseEntity.ok(memberLoginResponseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<MemberLoginResponseDto> reissue(@RequestBody TokenReissueRequestDto requestDto) {
        MemberLoginResponseDto memberLoginResponseDto = authService.reissueToken(requestDto);
        return ResponseEntity.ok(memberLoginResponseDto);
    }
}
