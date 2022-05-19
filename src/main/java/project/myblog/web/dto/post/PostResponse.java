package project.myblog.web.dto.post;

import project.myblog.domain.Post;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostResponse {
    private Long id;
    private String title;
    private String contents;
    private String author;
    private int hits;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public PostResponse(Long id, String title, String contents, String author, int hits, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.hits = hits;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public PostResponse(Long id, String title, String contents, String author, int hits) {
        this(id, title, contents, author, hits, null, null);
    }

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContents(), post.getMember().getEmail(), post.getHits(),
             post.getCreateDate(), post.getModifiedDate());
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

    public int getHits() {
        return hits;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return getHits() == that.getHits() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getContents(), that.getContents()) &&
                Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContents(), getAuthor(), getHits());
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", author='" + author + '\'' +
                ", hits=" + hits +
                '}';
    }
}
