package project.myblog.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.MemberService;
import project.myblog.web.dto.ApiResponse;
import project.myblog.web.dto.member.MemberIntroductionRequest;
import project.myblog.web.dto.member.MemberResponse;
import project.myblog.web.dto.member.MemberSubjectRequest;

import javax.validation.Valid;

@RequestMapping(value = "/members/me")
@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MemberResponse>> findMemberOfMine(@Login LoginMember loginMember) {
        MemberResponse member = memberService.findMemberOfMine(loginMember.getEmail());
        return ResponseEntity.ok(ApiResponse.success(member));
    }

    @PatchMapping(value = "/introduction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMemberOfMineIntroduction(@Login LoginMember loginMember,
                                                         @Valid @RequestBody MemberIntroductionRequest request) {
        memberService.updateMemberOfMineIntroduction(loginMember.getEmail(), request.getIntroduction());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/subject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMemberOfMineSubject(@Login LoginMember loginMember,
                                                            @Valid @RequestBody MemberSubjectRequest request) {
        memberService.updateMemberOfMineSubject(loginMember.getEmail(), request.getSubject());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@Login LoginMember loginMember) {
        memberService.deleteMember(loginMember.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
