package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MemberStepsRequest {
    public static final String NAVER_EMAIL ="monkeyDugi@gmail.com";
    public static final String JSESSIONID = "JSESSIONID";

    public static ExtractableResponse<Response> 내_회원_정보_조회_요청(RequestSpecification given, String sessionId) {
        return given.log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .when().get("members/me")
                .then().log().all()
                .extract();
    }


    public static ExtractableResponse<Response> 내_회원_정보_수정_요청_한줄_소개(RequestSpecification given,
                                                                         String sessionId,
                                                                         String introduction) {
        Map<String, String> params = new HashMap<>();
        params.put("introduction", introduction);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().patch("members/me/introduction")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_회원_정보_수정_요청_제목(RequestSpecification given,
                                                                      String sessionId,
                                                                      String subject) {
        Map<String, String> params = new HashMap<>();
        params.put("subject", subject);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().patch("members/me/subject")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_탈퇴_요청(RequestSpecification given, String sessionId) {
        return given.log().all()
                .cookie(JSESSIONID, sessionId)
                .when().delete("members/me")
                .then().log().all()
                .extract();
    }
}
