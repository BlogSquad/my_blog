package project.myblog.acceptance.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;
import project.myblog.web.dto.MemberRequest;

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


    public static ExtractableResponse<Response> 내_회원_정보_수정_요청(RequestSpecification given,
                                                                  String sessionId,
                                                                  MemberRequest memberRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("introduction", memberRequest.getIntroduction());
        params.put("subject", memberRequest.getSubject());

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .body(params)
                .when().patch("members/me")
                .then().log().all().extract();
    }
}
