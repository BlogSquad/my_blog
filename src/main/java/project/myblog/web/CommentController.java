package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.CommentService;
import project.myblog.web.dto.comment.CommentRequest;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/posts/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComment(@Login LoginMember loginMember, @PathVariable("postId") Long postId,
                                               @Valid @RequestBody CommentRequest commentRequest) {
        Long commentId = commentService.createComment(loginMember.getEmail(), postId, commentRequest);
        return ResponseEntity.created(URI.create("/posts/" + postId + "/comments/" + commentId)).build();
    }
}
