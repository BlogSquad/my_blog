package project.myblog.web.dto.member;

import project.myblog.domain.Member;

import java.util.Objects;

public class MemberResponse {
    private String email;
    private String introduction;
    private String subject;

    public MemberResponse(Member member) {
        this.email = member.getEmail();
        this.introduction = member.getIntroduction();
        this.subject = member.getSubject();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberResponse that = (MemberResponse) o;
        return Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getIntroduction(), that.getIntroduction()) &&
                Objects.equals(getSubject(), that.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getIntroduction(), getSubject());
    }

    @Override
    public String toString() {
        return "MemberResponse{" +
                "email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
