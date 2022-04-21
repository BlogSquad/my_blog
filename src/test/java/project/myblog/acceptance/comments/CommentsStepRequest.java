package project.myblog.acceptance.comments;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static project.myblog.acceptance.member.MemberStepsRequest.JSESSIONID;

public class CommentsStepRequest {
    public static ExtractableResponse<Response> 댓글_작성_요청(RequestSpecification given, String sessionId, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().post("/posts/{id}/comments", 1L)
                .then().log().all()
                .extract();
    }
}
