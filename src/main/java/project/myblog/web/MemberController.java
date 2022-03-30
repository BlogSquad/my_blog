package project.myblog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.service.AuthService;

@RestController
public class MemberController {
    private static String CLIENT_ID = "wxo1BZFKWHDc37EpRJa6";
    private static String SECRET_ID = "m8d1wUiuyD";
    private static String GRANT_TYPE = "authorization_code";

//    @PostMapping(value = "/members", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
//        return ResponseEntity.created(URI.create("/members/" + 1L)).build();
//    }

    private final AuthService authService;

    public MemberController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 테스트를 위한거. 프론트 서버가 없으니까
     * /naver
     * @param code
     */
    @GetMapping("/login/oauth2/code/naver")
    public String singUpOrLogin(@RequestParam String code) {
        return authService.getOAuthEmail(code);
        // 1. code + client_id + client_secret으로 accesstoken발급, restTemplate

        // 2. accessToken으로 resource_owner의 회원정보(email...) 가져오기, restTemplate
        // 2-1. 여기서 로그인은 됨.

        // 3. 없는 email이면, 회원 가입을 하도록 email만 제공
        // 3. 가입된 email이면, 바로 로그인 처리하고, email에 추가로 회원 정보 제공

        // 처음에는 백엔드에서 회원가입 페이지로 리다이렉트를 생각했지만,
        // 프론트에서 승인 코드까지 받고 우리에게 이메일 달라고 요청한 것 뿐이니까 회원 가입 페이지는 전혀 무관함.
    }
}
