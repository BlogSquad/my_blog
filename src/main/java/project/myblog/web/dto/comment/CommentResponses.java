package project.myblog.web.dto.comment;

import project.myblog.domain.Comment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommentResponses {
    private List<CommentResponse> comments;

    private CommentResponses(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public static CommentResponses create(List<Comment> parentComments) {
        return new CommentResponses(commentToList(parentComments));
    }

    private static List<CommentResponse> commentToList(List<Comment> parentComments) {
        return parentComments.stream()
                .filter(comment -> !comment.isAllDeleted())
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
