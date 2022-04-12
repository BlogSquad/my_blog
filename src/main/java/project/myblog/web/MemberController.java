package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.domain.Member;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.MemberResponse;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberResponse findMemberOfMine(@Login LoginMember loginMember) {
        Member member = memberService.findMemberOfMine(loginMember);
        return new MemberResponse(member);
    }
}
