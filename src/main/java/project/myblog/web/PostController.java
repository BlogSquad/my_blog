package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.PostService;
import project.myblog.web.dto.post.PostRequest;
import project.myblog.web.dto.post.PostResponse;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPost(@Login LoginMember loginMember, @Valid @RequestBody PostRequest postRequest) {
        Long id = postService.createPost(loginMember.getEmail(), postRequest);
        return ResponseEntity.created(URI.create("/posts/" + id)).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> findPost(@PathVariable Long id) {
        PostResponse postResponse = postService.findPost(id);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePost(@Login LoginMember loginMember,
                                           @PathVariable Long id, @Valid @RequestBody PostRequest postRequest) {
        postService.updatePost(loginMember.getEmail(), id, postRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@Login LoginMember loginMember, @PathVariable Long id) {
        postService.deletePost(loginMember.getEmail(), id);
        return ResponseEntity.noContent().build();
    }
}
