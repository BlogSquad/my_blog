package project.myblog.acceptance.comment;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static project.myblog.acceptance.member.MemberStepsRequest.JSESSIONID;

public class CommentStepsRequest {
    public static ExtractableResponse<Response> 댓글_작성_요청(RequestSpecification given, String sessionId, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().post("/posts/{postId}/comments", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글_목록_조회_요청(RequestSpecification given) {
        return given.log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts/{postId}/comments", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글_수정_요청(RequestSpecification given, String sessionId, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().put("/comments/{commentId}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글_삭제_요청(RequestSpecification given, String sessionId) {
        return given.log().all()
                .cookie(JSESSIONID, sessionId)
                .when().delete("/comments/{commentId}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 대댓글_작성_요청(RequestSpecification given, String sessionId, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().post("/posts/{postId}/comments/{parentId}", 1L, 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 대대댓글_작성_요청(RequestSpecification given, String sessionId, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().post("/posts/{postId}/comments/{parentId}", 1L, 2L)
                .then().log().all()
                .extract();
    }
}
