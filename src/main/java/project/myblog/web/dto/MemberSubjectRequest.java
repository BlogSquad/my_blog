package project.myblog.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class MemberSubjectRequest {
    @NotNull(message = "제목은 null일 수 없습니다.")
    @NotEmpty(message = "제목은 빈 값일 수 없습니다.")
    @NotBlank(message = "제목은 공백일 수 없습니다.")
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
