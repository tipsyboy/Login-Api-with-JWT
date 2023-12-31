package study.tipsyboy.loginApiJwt.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import study.tipsyboy.loginApiJwt.dto.auth.MemberLoginRequestDto;
import study.tipsyboy.loginApiJwt.dto.auth.MemberSignupRequestDto;

@RequestMapping("/auth")
@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String loginForm(@ModelAttribute MemberLoginRequestDto memberLoginRequestDto) {
        return "auth/loginForm";
    }

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute MemberSignupRequestDto memberSignupRequestDto) {
        return "auth/signupForm";
    }

}
