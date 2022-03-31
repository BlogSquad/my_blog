package project.myblog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.authentication.Login;
import project.myblog.web.dto.SessionMember;

@RestController
public class MemberController {
    // 로그인 테스트용
    @GetMapping("/")
    public String home(@Login SessionMember sessionMember) {
        if (sessionMember == null) {
            System.out.println("세션에 데이터가 없음. 비회원으로 제공");
            return "세션에 데이터가 없음. 비회원으로 제공";
        }

        System.out.println("sessionMember = " + sessionMember);
        return "세션이 있음 : " + sessionMember;
    }

    @GetMapping("/posts/1")
    public String post(@Login SessionMember sessionMember) {
        if (sessionMember == null) {
            return "세션에 데이터가 없음. 글쓰기 불가";
        }

        System.out.println("sessionMember = " + sessionMember);
        return "세션이 있음 : 글쓰기 가능";
    }
}
