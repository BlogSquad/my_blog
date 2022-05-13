package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.CommentService;
import project.myblog.web.dto.ApiResponse;
import project.myblog.web.dto.comment.CommentRequest;
import project.myblog.web.dto.comment.CommentResponse;
import project.myblog.web.dto.comment.CommentResponses;

import javax.validation.Valid;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/posts/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@Login LoginMember loginMember, @PathVariable("postId") Long postId,
                                              @Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.createComment(loginMember.getEmail(), postId, commentRequest);
        return ResponseEntity.ok(ApiResponse.success(commentResponse));
    }

    @GetMapping(value = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CommentResponses>> findComments(@PathVariable("postId") Long postId) {
        CommentResponses commentResponses = commentService.findComments(postId);
        return ResponseEntity.ok(ApiResponse.success(commentResponses));
    }

    @PutMapping(value = "/comments/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateComment(@Login LoginMember loginMember,
                                              @PathVariable("commentId") Long commentId,
                                              @Valid @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(loginMember.getEmail(), commentId, commentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@Login LoginMember loginMember,
                                              @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(loginMember.getEmail(), commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/posts/{postId}/comments/{parentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CommentResponse>> createChildComment(@Login LoginMember loginMember,
                                                    @PathVariable("postId") Long postId,
                                                    @PathVariable("parentId") Long parentId,
                                                    @Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.createChildComment(loginMember.getEmail(), postId, parentId, commentRequest);
        return ResponseEntity.ok(ApiResponse.success(commentResponse));
    }
}
