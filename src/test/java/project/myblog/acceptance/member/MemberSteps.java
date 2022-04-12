package project.myblog.acceptance.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemberSteps {
    public static ExtractableResponse<Response> 내_회원_정보_조회_요청(String sessionId) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie("JSESSIONID", sessionId)
                .when().get("members/me")
                .then().log().all().extract();
    }

    public static void 내_회원_정보_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("email")).isEqualTo("monkeyDugi@gmail.com");
        assertThat(response.jsonPath().getString("introduction")).isEqualTo("한줄 소개");
        assertThat(response.jsonPath().getString("subject")).isEqualTo("monkeyDugi");
    }

    public static void 내_회원_정보_조회_안됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.sessionId()).isNullOrEmpty();
    }
}
