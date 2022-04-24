package project.myblog.web.dto.comment;

import java.util.Objects;

public class CommentResponse {
    private String contents;
    private String author;

    public CommentResponse(String contents, String author) {
        this.contents = contents;
        this.author = author;
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
        CommentResponse that = (CommentResponse) o;
        return Objects.equals(getContents(), that.getContents()) && Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContents(), getAuthor());
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "contents='" + contents + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
