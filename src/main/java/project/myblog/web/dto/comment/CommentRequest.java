package project.myblog.web.dto.comment;

import project.myblog.domain.Comment;
import project.myblog.domain.Member;
import project.myblog.domain.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CommentRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 1_000)
    private String contents;

    protected CommentRequest() {
    }

    public CommentRequest(String contents) {
        this.contents = contents;
    }

    public Comment toEntity(Post post, Member member) {
        return new Comment(contents, post, member);
    }

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentRequest that = (CommentRequest) o;
        return Objects.equals(getContents(), that.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContents());
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
                "contents='" + contents + '\'' +
                '}';
    }
}
