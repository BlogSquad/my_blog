package project.myblog.web.dto.post.response;

import project.myblog.domain.post.Post;

import java.util.Objects;

public class PostResponse {
    private String title;
    private String contents;
    private String author;

    public PostResponse(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getMember().getEmail();
    }

    public PostResponse(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContents(), that.getContents()) && Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContents(), getAuthor());
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
