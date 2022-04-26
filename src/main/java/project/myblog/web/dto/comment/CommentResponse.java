package project.myblog.web.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import project.myblog.domain.Comment;

import java.time.LocalDateTime;
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
    private List<CommentResponse> children;

    public static CommentResponses create(List<Comment> parentComments) {
        return new CommentResponses(toList(parentComments));
    }

    private CommentResponse(Comment comment) {
        this.parentId = comment.getParent() == null? null : comment.getParent().getId();
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.author = comment.getMember().getEmail();
        this.createDate = comment.getCreateDate();
        this.modifiedDate = comment.getModifiedDate();
        this.children = toList(comment.getChildren());
    }

    private static List<CommentResponse> toList(List<Comment> parentComments) {
        return parentComments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
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
