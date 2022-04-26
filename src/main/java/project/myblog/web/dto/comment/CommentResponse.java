package project.myblog.web.dto.comment;

import project.myblog.domain.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentResponse {
    private Long parentId;
    private Long commentId;
    private String contents;
    private String author;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private List<CommentResponse> children = new ArrayList<>();

    public CommentResponse(Long parentId, Long commentId, String contents, String author, LocalDateTime createDate, LocalDateTime modifiedDate, List<Comment> children) {
        this.parentId = parentId;
        this.commentId = commentId;
        this.contents = contents;
        this.author = author;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment child : children) {
            commentResponses.add(new CommentResponse(
                    child.getParent().getId(), child.getId(), child.getContents(), child.getMember().getEmail(),
                    child.getCreateDate(), child.getModifiedDate(), child.getChildren()
            ));
        }
        this.children = commentResponses;
    }

    public CommentResponse(Long commentId, String contents, String author, LocalDateTime createDate, LocalDateTime modifiedDate, List<Comment> children) {
        this.parentId = null;
        this.commentId = commentId;
        this.contents = contents;
        this.author = author;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment child : children) {
            commentResponses.add(new CommentResponse(
                    child.getParent().getId(), child.getId(), child.getContents(), child.getMember().getEmail(),
                    child.getCreateDate(), child.getModifiedDate(), child.getChildren()
            ));
        }
        this.children = commentResponses;
    }

    public CommentResponse(String contents, String author) {
        this.contents = contents;
        this.author = author;
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
