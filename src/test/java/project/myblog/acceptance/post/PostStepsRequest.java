package project.myblog.acceptance.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static project.myblog.acceptance.member.MemberStepsRequest.JSESSIONID;
import static project.myblog.acceptance.post.PostStepsAssert.포스트_작성됨;

public class PostStepsRequest {
    public static ExtractableResponse<Response> 포스트_작성_요청(
            RequestSpecification given, String sessionId, String title, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().post("/posts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 포스트_조회_요청(RequestSpecification given) {
        return given.log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts/{id}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 포스트_목록_조회_요청(RequestSpecification given, int page) {
        return given.log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", page)
                .when().get("/posts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 포스트_수정_요청(RequestSpecification given, String sessionId, String title, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().put("/posts/{id}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 포스트_삭제_요청(RequestSpecification given, String sessionId) {
        return given.log().all()
                .cookie(JSESSIONID, sessionId)
                .when().delete("/posts/{id}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 포스트_작성_되어있음(String sessionId, String title, String contents) {
        ExtractableResponse<Response> response = 포스트_작성_요청(given(), sessionId, title, contents);
        포스트_작성됨(response);
        return response;
    }
}
