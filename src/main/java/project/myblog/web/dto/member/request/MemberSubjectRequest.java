package project.myblog.web.dto.member.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class MemberSubjectRequest {
    @NotNull(message = "[Request] 제목은 null일 수 없습니다.")
    @NotBlank(message = "[Request] 제목은 공백일 수 없습니다.")
    @Size(min = 1, max = 30)
    private String subject;

    protected MemberSubjectRequest() {
    }

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
