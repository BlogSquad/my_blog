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
import project.myblog.service.CommentService;
import project.myblog.web.dto.comment.CommentRequest;
import project.myblog.web.dto.comment.CommentResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentResponse>> findComment(@PathVariable("postId") Long postId) {
        CommentResponse commentResponse1 = new CommentResponse("댓글1", "monkeyDugi@gmail.com");
        CommentResponse commentResponse2 = new CommentResponse("댓글2", "monkeyDugi@gmail.com");

        List<CommentResponse> responses = new ArrayList<>();
        responses.add(commentResponse1);
        responses.add(commentResponse2);

        return ResponseEntity.ok(responses);
    }
}
