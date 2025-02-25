package com.example.membership.Controller;

import com.example.membership.DTO.MemberDTO;
import com.example.membership.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor // final 필드에 자동으로 생성자 주입을 해줌 (밑에 MemberService의 경우)
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;

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

    @GetMapping("/membership/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // html로 가져갈 데이터가 있다면 Model 사용
        model.addAttribute("memberList", memberDTOList);

        return "list";
    }

    @GetMapping("/membership/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);

        return "detail";
    }

    @GetMapping("/membership/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);

        return "update";
    }

    @PostMapping("/membership/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/membership/" + memberDTO.getId();
    }

}
