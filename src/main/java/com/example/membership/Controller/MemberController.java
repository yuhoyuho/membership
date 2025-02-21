package com.example.membership.Controller;

import com.example.membership.DTO.MemberDTO;
import com.example.membership.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor // 자동으로 생성자 주입을 해줌 (밑에 MemberService의 경우)
public class MemberController {

    // 생성자 주입
    private MemberService memberService;

    @GetMapping("/membership/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/membership/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/membership/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/membership/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);

        if(loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }
        else {
            return "login";
        }
    }
}
