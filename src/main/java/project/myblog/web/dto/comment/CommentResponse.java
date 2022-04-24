package project.myblog.web.dto.comment;

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
}
