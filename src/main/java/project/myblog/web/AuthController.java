package project.myblog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login/oauth2/code/naver")
    public void login(@RequestParam String code, HttpServletRequest request) {
        authService.login(code, request);
    }

    @GetMapping("/logout/oauth2/code/naver")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

    @GetMapping("/dugi")
    public void dugi(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println("session.getAttribute() = " + session.getAttribute("sessionResponse"));
    }
}
