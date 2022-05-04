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
                .filter(comment -> !isDelete(comment))
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    private static boolean isDelete(Comment comment) {
        // 댓글과 대댓글이 모두 삭제된 경우
        if (comment.isDeleted() && comment.getChildren().stream().allMatch(Comment::isDeleted)) {
            return true;
        // 댓글이 삭제되고, 대댓글은 삭제되지 않은 경우
        } else if (comment.isDeleted() && !comment.getChildren().stream().allMatch(Comment::isDeleted)) {
            comment.update("삭제된 댓글입니다.", comment.getMember());
            return false;
        }
        // 댓글이 삭제되지 않고, 대댓글이 모두 삭제된 경우
        return false;
    }

    private CommentResponse(Comment comment) {
        if (comment.getParent() == null) {
            this.parentId = null;
            // 삭제되지 않은 대댓글 필터링
            this.children = comment.getChildren().stream()
                    .filter(nestedComment -> !nestedComment.isDeleted())
                    .map(CommentResponse::new)
                    .collect(Collectors.toList());
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
