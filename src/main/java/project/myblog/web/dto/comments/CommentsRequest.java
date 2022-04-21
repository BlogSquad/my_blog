package project.myblog.web.dto.comments;

import project.myblog.domain.Comments;
import project.myblog.domain.Member;
import project.myblog.domain.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CommentsRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 1_000)
    private String contents;

    protected CommentsRequest() {
    }

    public CommentsRequest(String contents) {
        this.contents = contents;
    }

    public Comments toEntity(Post post, Member member) {
        return new Comments(contents, post, member);
    }

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentsRequest that = (CommentsRequest) o;
        return Objects.equals(getContents(), that.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContents());
    }

    @Override
    public String toString() {
        return "CommentsRequest{" +
                "contents='" + contents + '\'' +
                '}';
    }
}
