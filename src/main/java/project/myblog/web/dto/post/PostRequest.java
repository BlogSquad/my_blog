package project.myblog.web.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import project.myblog.domain.Post;
import project.myblog.domain.Member;

public class PostRequest {
    @NotNull
    @NotBlank
    @javax.validation.constraints.NotEmpty
    private String title;
    @NotNull
    @NotBlank
    @javax.validation.constraints.NotEmpty
    private String contents;

    protected PostRequest() {
    }

    public PostRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Post toEntity(Member member) {
        return new Post(title, contents, member);
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostRequest that = (PostRequest) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContents(), that.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContents());
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
