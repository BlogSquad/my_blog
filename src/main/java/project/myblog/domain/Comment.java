package project.myblog.domain;

import project.myblog.exception.BusinessException;

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
import static project.myblog.exception.ExceptionCode.POST_COMMENT_AUTHORIZATION;

@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @JoinColumn(name = "POST_ID", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Post post;

    @JoinColumn(name = "MEMBER_ID", nullable = false)
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

    public void update(String contents, Member member) {
        validateOwner(member);
        this.contents = contents;
    }

    public void validateOwner(Member member) {
        if (!isOwner(member)) {
            throw new BusinessException(POST_COMMENT_AUTHORIZATION);
        }
    }

    private boolean isOwner(Member member) {
        return this.member.equals(member);
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
