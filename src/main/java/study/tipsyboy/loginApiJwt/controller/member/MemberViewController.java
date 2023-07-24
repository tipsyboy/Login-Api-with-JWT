package study.tipsyboy.loginApiJwt.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MemberViewController {

    @GetMapping("/member/info")
    public String myInfoView() {
        return "members/memberInfo";
    }

    @GetMapping("/member/info/{memberName}")
    public String myInfoView(@PathVariable String memberName,
                             Model model) {
        model.addAttribute("memberName", memberName);
        return "members/memberInfo";
    }
}
