package project.myblog.web.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import project.myblog.domain.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CommentResponse {
    private Long parentId;
    private Long commentId;
    private String contents;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private List<CommentResponse> children = new ArrayList<>();

    public static CommentResponse create(Comment comment) {
        return new CommentResponse(comment);
    }

    public static CommentResponses create(List<Comment> parentComments) {
        return new CommentResponses(toList(parentComments));
    }

    private static List<CommentResponse> toList(List<Comment> parentComments) {
        return parentComments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    private CommentResponse(Comment comment) {
        if (comment.getParent() == null) {
            this.parentId = null;
            this.children = toList(comment.getChildren());
        } else {
            this.parentId = comment.getParent().getId();
        }

        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.author = comment.getMember().getEmail();
        this.createDate = comment.getCreateDate();
        this.modifiedDate = comment.getModifiedDate();
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public List<CommentResponse> getChildren() {
        return Collections.unmodifiableList(children);
    }
}
