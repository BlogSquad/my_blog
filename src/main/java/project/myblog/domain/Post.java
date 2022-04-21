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
import project.myblog.exception.BusinessException;

import static javax.persistence.FetchType.LAZY;
import static project.myblog.exception.ExceptionCode.POST_AUTHORIZATION;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Lob
    private String contents;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    protected Post() {
    }

    public Post(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    public Post(Long id, String title, String contents, Member member) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    public void update(String title, String contents, Member member) {
        validateOwner(member);
        this.title = title;
        this.contents = contents;
    }

    public void validateOwner(Member member) {
        if (!isOwner(member)) {
            throw new BusinessException(POST_AUTHORIZATION);
        }
    }

    private boolean isOwner(Member member) {
        return this.member.equals(member);
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

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(title, post.title) &&
                Objects.equals(contents, post.contents) &&
                Objects.equals(member, post.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, contents, member);
    }
}
