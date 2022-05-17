package project.myblog.web.dto.comment;

import java.util.Collections;
import java.util.List;

public class CommentResponses {
    private List<CommentResponse> comments;

    public CommentResponses(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public List<CommentResponse> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
