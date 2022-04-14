package project.myblog.web.dto;

import java.util.Objects;

public class MemberSubjectRequest {
    private String subject;

    public MemberSubjectRequest(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberSubjectRequest that = (MemberSubjectRequest) o;
        return Objects.equals(getSubject(), that.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject());
    }

    @Override
    public String toString() {
        return "MemberSubjectRequest{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
