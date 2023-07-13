package study.tipsyboy.loginApiJwt.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.loginApiJwt.dto.auth.MemberSignupRequestDto;
import study.tipsyboy.loginApiJwt.dto.auth.MemberSignupResponseDto;
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
        log.info("requestDto.getName={}", requestDto.getMemberName());
        log.info("requestDto.getPassword={}", requestDto.getPassword());
        Long memberId = authService.signup(requestDto);

        return ResponseEntity.ok(new MemberSignupResponseDto(memberId));
    }
}
