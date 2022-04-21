package project.myblog.web.dto.post;

import project.myblog.domain.Post;

import java.util.Objects;

public class PostResponse {
    private Long id;
    private String title;
    private String contents;
    private String author;

    public PostResponse(Long id, String title, String contents, String author) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContents(), post.getMember().getEmail());
    }

    public Long getId() {
        return id;
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
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContents(), that.getContents()) && Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContents(), getAuthor());
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
