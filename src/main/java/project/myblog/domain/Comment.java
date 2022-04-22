package project.myblog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @JoinColumn(name = "POST_ID")
    @ManyToOne(fetch = LAZY)
    private Post post;

    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = LAZY)
    private Member member;

    protected Comment() {
    }

    public Comment(String contents, Post post, Member member) {
        this.contents = contents;
        this.post = post;
        this.member = member;
    }

    public Comment(Long id, String contents, Post post, Member member) {
        this.id = id;
        this.contents = contents;
        this.post = post;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public Post getPost() {
        return post;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId()) &&
                Objects.equals(getContents(), comment.getContents()) &&
                Objects.equals(getPost(), comment.getPost()) &&
                Objects.equals(getMember(), comment.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContents(), getPost(), getMember());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", post=" + post +
                ", member=" + member +
                '}';
    }
}
