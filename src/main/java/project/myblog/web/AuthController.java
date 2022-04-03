package project.myblog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {
    @GetMapping("/logout/oauth2/code/naver")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

    @GetMapping("/login/oauth2/code/naver")
    public void login(@RequestParam String code) {
        System.out.println("code = " + code);
    }
}
