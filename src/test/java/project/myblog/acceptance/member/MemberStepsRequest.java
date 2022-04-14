package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;
import project.myblog.web.dto.MemberIntroductionRequest;
import project.myblog.web.dto.MemberSubjectRequest;

import java.util.HashMap;
import java.util.Map;

public class MemberStepsRequest {
    public static ExtractableResponse<Response> 내_회원_정보_조회_요청(RequestSpecification given, String sessionId) {
        return given.log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .when().get("members/me")
                .then().log().all().extract();
    }


    public static ExtractableResponse<Response> 내_회원_정보_수정_요청_한줄_소개(RequestSpecification given,
                                                                    String sessionId,
                                                                    MemberIntroductionRequest memberIntroductionRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("introduction", memberIntroductionRequest.getIntroduction());

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .body(params)
                .when().patch("members/me/introduction")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 내_회원_정보_수정_요청_제목(RequestSpecification given,
                                                                 String sessionId,
                                                                 MemberSubjectRequest memberSubjectRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("subject", memberSubjectRequest.getSubject());

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .body(params)
                .when().patch("members/me/subject")
                .then().log().all().extract();
    }
}
