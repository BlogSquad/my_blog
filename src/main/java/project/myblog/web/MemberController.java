package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.authentication.Login;
import project.myblog.web.dto.MemberResponse;
import project.myblog.web.dto.LoginMember;

@RestController
public class MemberController {
    @GetMapping(value = "/members/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberResponse findMemberOfMine(@Login LoginMember sessionMember) {
        return new MemberResponse(sessionMember.getEmail());
    }
}
