package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.auth.dto.Login;
import project.myblog.auth.dto.LoginMember;
import project.myblog.service.CommentsService;
import project.myblog.web.dto.comments.CommentsRequest;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping(value = "/posts/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComments(@Login LoginMember loginMember, @PathVariable Long id,
                                               @Valid @RequestBody CommentsRequest commentsRequest) {
        Long commentsId = commentsService.createComments(loginMember.getEmail(), id, commentsRequest);
        return ResponseEntity.created(URI.create("/posts/" + commentsId + "/comments")).build();
    }
}
