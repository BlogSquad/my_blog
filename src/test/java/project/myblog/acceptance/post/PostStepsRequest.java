package project.myblog.acceptance.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class PostStepsRequest {
    public static final String JSESSIONID = "JSESSIONID";

    public static ExtractableResponse<Response> 포스트_작성_요청(
            RequestSpecification given, String sessionId, String title, String contents) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("contents", contents);

        return given.log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(JSESSIONID, sessionId)
                .body(params)
                .when().post("/post")
                .then().log().all().extract();
    }
}
