package project.myblog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotNull(message = "[Entity] 이메일은 null일 수 없습니다.")
    @NotEmpty(message = "[Entity] 이메일은 빈 값일 수 없습니다.")
    private String email;
    @NotNull(message = "[Entity] 한줄 소개는 null일 수 없습니다.")
    @NotBlank(message = "[Entity] 한줄 소개는 공백일 수 없습니다.")
    @Size(min = 1, max = 30)
    private String introduction;
    @NotNull(message = "[Entity] 제목은 null일 수 없습니다.")
    @NotBlank(message = "[Entity] 제목은 공백일 수 없습니다.")
    @Size(min = 1, max = 30)
    private String subject;

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

    private String extractEmailId(String email) {
        return email.substring(0, email.indexOf("@"));
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
