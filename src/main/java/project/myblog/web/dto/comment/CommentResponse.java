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
        return new CommentResponses(commentToList(parentComments));
    }

    private static List<CommentResponse> commentToList(List<Comment> parentComments) {
        return parentComments.stream()
                .filter(comment -> !comment.isAllDeleted())
                .map(comment ->  {
                    comment.updateIfDeletedCommentAndChildNotDeleted();
                    return new CommentResponse(comment);
                })
                .collect(Collectors.toList());
    }

    private CommentResponse(Comment comment) {
        this.parentId = comment.getParentId();
        if (!comment.isPresentParent()) {
            this.children = childCommentToList(comment);
        }

        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.author = comment.getMember().getEmail();
        this.createDate = comment.getCreateDate();
        this.modifiedDate = comment.getModifiedDate();
    }

    private List<CommentResponse> childCommentToList(Comment comment) {
        return comment.getChildren().stream()
                .filter(childComment -> !childComment.isDeleted())
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
