package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.post.PostService;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.response.PostResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPost(@Login LoginMember loginMember, @Valid @RequestBody PostRequest postRequest) {
        Long id = postService.createPost(loginMember.getEmail(), postRequest);
        return ResponseEntity.created(URI.create("/posts/" + id)).build();
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> findPost(@PathVariable Long id) {
        PostResponse postResponse = new PostResponse("포스트1제목", "포스트1내용", "monkeyDugi@gmail.com");
        return ResponseEntity.ok(postResponse);
    }
}
