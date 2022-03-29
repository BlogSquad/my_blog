package project.myblog.acceptance.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberSteps {
    private static final String ID = "monkeydugi";
    private static final String EMAIL = "email@emai.com";
    private static final String NAME = "이병덕";
    private static final String INTRODUCTION = "한줄 소개";

    public static Map<String, String> 회원_생성_정보() {
        Map<String, String> params = new HashMap<>();
        params.put("id", ID);
        params.put("email", EMAIL);
        params.put("name", NAME);
        params.put("introduction", INTRODUCTION);

        return params;
    }

    public static ExtractableResponse<Response> 회원_생성_요청(Map<String, String> params) {
        return 회원_생성_요청(RestAssured.given(), params);
    }

    public static ExtractableResponse<Response> 회원_생성_요청(RequestSpecification given, Map<String, String> params) {
        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/members")
                .then().log().all()
                .extract();
    }

    static void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotEmpty();
    }
}
