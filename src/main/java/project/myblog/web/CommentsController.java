package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;

import java.net.URI;

@RestController
public class CommentsController {
    @PostMapping(value = "/posts/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComments(@Login LoginMember loginMember) {
        return ResponseEntity.created(URI.create("/posts/1/comments")).build();
    }
}
