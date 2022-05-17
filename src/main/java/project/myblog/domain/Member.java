package project.myblog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String introduction;

    @Column(length = 30, nullable = false)
    private String subject;

    @Column(nullable = false)
    private boolean isDeleted = false;

    protected Member() {
    }

    public Member(String email) {
        this.email = email;
        this.introduction = "한줄 소개가 작성되지 않았습니다.";
        this.subject = extractEmailId(email);
    }

    public Member(String email, String introduction, String subject) {
        this.email = email;
        this.introduction = introduction;
        this.subject = subject;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void updateSubject(String subject) {
        this.subject = subject;
    }

    public void delete() {
        isDeleted = true;
    }

    private String extractEmailId(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) &&
                Objects.equals(getEmail(), member.getEmail()) &&
                Objects.equals(getIntroduction(), member.getIntroduction()) &&
                Objects.equals(getSubject(), member.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getIntroduction(), getSubject());
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

}
