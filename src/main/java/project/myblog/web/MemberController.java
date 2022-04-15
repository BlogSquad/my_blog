package project.myblog.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.domain.Member;
import project.myblog.service.member.MemberService;
import project.myblog.web.dto.member.request.MemberIntroductionRequest;
import project.myblog.web.dto.member.response.MemberResponse;
import project.myblog.web.dto.member.request.MemberSubjectRequest;

import javax.validation.Valid;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberResponse findMemberOfMine(@Login LoginMember loginMember) {
        Member member = memberService.findMemberOfMine(loginMember.getEmail());
        return new MemberResponse(member);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/members/me/introduction", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateMemberOfMineIntroduction(@Login LoginMember loginMember,
                                               @Valid @RequestBody MemberIntroductionRequest request) {
        memberService.updateMemberOfMineIntroduction(loginMember.getEmail(), request.getIntroduction());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/members/me/subject", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateMemberOfMineSubject(@Login LoginMember loginMember,
                                          @Valid @RequestBody MemberSubjectRequest request) {
        memberService.updateMemberOfMineSubject(loginMember.getEmail(), request.getSubject());
    }
}
