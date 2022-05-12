package project.myblog.domain;

import project.myblog.exception.BusinessException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static project.myblog.exception.ErrorCode.COMMENT_AUTHORIZATION;

@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @JoinColumn(name = "PARENT_ID")
    @ManyToOne(fetch = LAZY)
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Comment> children = new ArrayList<>();

    @JoinColumn(name = "POST_ID", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Post post;

    @JoinColumn(name = "MEMBER_ID", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(nullable = false)
    private boolean isDeleted = false;

    protected Comment() {
    }

    public Comment(String contents, Post post, Member member) {
        this(null, contents, post, member);
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

    public void delete(Member member) {
        validateOwner(member);
        this.isDeleted = true;
    }

    public Comment createChildComment(Comment comment){
        if (this.parent == null) {
            comment.parent = this;
            children.add(comment);
            return this;
        }
        comment.parent = this.parent;
        this.parent.addChildren(comment);
        return this.parent;
    }

    public boolean isAllDeleted() {
        return isDeleted && children.stream().allMatch(Comment::isDeleted);
    }

    public boolean isPresentParent() {
        return parent != null;
    }

    public Long getParentId() {
        return parent == null ? null : parent.getId();
    }

    protected void addChildren(Comment comment) {
        this.children.add(comment);
    }

    private void validateOwner(Member member) {
        if (!isOwner(member)) {
            throw new BusinessException(COMMENT_AUTHORIZATION);
        }
    }

    /**
     * 댓글이 삭제되고, 대댓글이 삭제되지 않을 경우 판단
     *
     * @return 댓글이 삭제되고, 대댓글이 하나라도 삭제되지 않은 경우 true
     *         댓글이 삭제되고, 대댓글이 모두 삭제된 경우 false
     *         댓글이 삭제되지 않고, 대댓글이 하나라도 삭제되지 않은 경우 false
     *         댓글이 삭제되지 않고, 대댓글이 모두 삭제된 경우 false
     */
    public boolean isChildRemained() {
        return isDeleted && !children.stream().allMatch(Comment::isDeleted);
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

    public Comment getParent() {
        return parent;
    }

    public List<Comment> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public Post getPost() {
        return post;
    }

    public Member getMember() {
        return member;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId()) && Objects.equals(getContents(), comment.getContents()) && Objects.equals(getPost(), comment.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContents(), getMember());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", parent=" + parent +
                ", post=" + post +
                '}';
    }
}
