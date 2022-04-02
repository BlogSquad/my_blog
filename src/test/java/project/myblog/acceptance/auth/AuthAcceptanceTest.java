package project.myblog.acceptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import project.myblog.AcceptanceTest;

/**
 * Feat 회원 정보를 관리
 *
 * Scenario 회원가입
 * When 회원 생성을 요청한다
 * Then 회원 생성된다
 *
 * Scenario 회원정보 조회
 * Given 로그인 되어 있음
 * When 회원 정보 조회 요청
 * Then 회원 정보 조회됨
 *
 * Scenario 회원정보 수정
 * Given 로그인 되어 있음
 * When 회원 정보 수정 요청
 * Then 회원 정보 수정됨
 */
class AuthAcceptanceTest extends AcceptanceTest {
    /**
     * Scenario 회원가입
     * When 회원 생성을 요청한다
     * 사용자가 login누르면 네이버 창 뜨고
     * 네이버 창에서 id, pwd로 로그인하면 (scope동의 html, ) 네이버에서 사용자를 redirecturl+code로 리다이렉트 시킴
     * 사용자가 authorization code
     * naver client id, secret ->
     * access token 받음 ->
     * access token 으로 naver 한테 api 요청->
     * naver 가 json으로 user info 응답.
     * <p>
     * <p>
     * 가라 code <- 맘대로 만듬
     * 네이버 호출에 대한 응답도 맘대로 만듬.
     * <p>
     * (네이버 한테 acces token 요청하는) 역할을 인터페이스로 <- 뽑고
     * 이걸 하위 클래스 한테 구현을 시킨다.
     * 이러면 네이버 깃헙 구글 등 클래스 추가하면 굿굿
     * <p>
     * <p>
     * <p>
     * Then 회원 생성된다
     *
     * Scenario 회원가입
     * When 회원 생성을 요청한다
     * Then 회원 생성된다
     */
    @Test
    void 회원_가입_세션() {
        회원_생성_요청();
    }

    public static ExtractableResponse<Response> 회원_생성_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/login/oauth2/code/naver")
                .then().log().all().extract();
    }
}
