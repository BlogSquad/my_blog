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
import static project.myblog.exception.ErrorCode.POST_AUTHORIZATION;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column(nullable = false)
    private int hits;

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

    public void delete(Member member) {
        validateOwner(member);
        this.isDeleted = true;
    }

    public void increaseHits(int hits) {
        this.hits += hits;
    }

    private void validateOwner(Member member) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public int getHits() {
        return hits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(title, post.title) &&
                Objects.equals(contents, post.contents) &&
                Objects.equals(member, post.member) &&
                Objects.equals(hits, post.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, contents, member, hits);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", member=" + member +
                ", hits=" + hits +
                '}';
    }
}
