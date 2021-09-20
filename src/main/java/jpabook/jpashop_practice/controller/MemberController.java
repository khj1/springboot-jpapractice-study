package jpabook.jpashop_practice.controller;

import jpabook.jpashop_practice.domain.Address;
import jpabook.jpashop_practice.domain.Member;
import jpabook.jpashop_practice.service.MemberService;
import jpabook.jpashop_practice.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @GetMapping("/members/new")
    public String createForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = Member.create(form);

        memberService.join(member);
        return "redirect:/";
    }

    // 전체 조회
    @GetMapping("/members")
    public String findAll(Model model) {

        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
