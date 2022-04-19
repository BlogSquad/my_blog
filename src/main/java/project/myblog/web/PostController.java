package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.post.PostService;
import project.myblog.web.dto.post.PostRequest;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPost(@Login LoginMember loginMember, @Valid @RequestBody PostRequest postRequest) {
        Long id = postService.createPost(loginMember.getEmail(), postRequest);
        return ResponseEntity.created(URI.create("/post/" + id)).build();
    }
}
