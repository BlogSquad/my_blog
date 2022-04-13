package project.myblog.web.dto;

import java.util.Objects;

public class MemberRequest {
    private String introduction;
    private String subject;

    public MemberRequest(String introduction, String subject) {
        this.introduction = introduction;
        this.subject = subject;
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
        MemberRequest that = (MemberRequest) o;
        return Objects.equals(getIntroduction(), that.getIntroduction()) &&
                Objects.equals(getSubject(), that.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntroduction(), getSubject());
    }

    @Override
    public String toString() {
        return "MemberRequest{" +
                "introduction='" + introduction + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
