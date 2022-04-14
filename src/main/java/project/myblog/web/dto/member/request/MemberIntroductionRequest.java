package project.myblog.web.dto.member.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class MemberIntroductionRequest {
    @NotNull(message = "[Request] 한줄 소개는 null일 수 없습니다.")
    @NotBlank(message = "[Request] 한줄 소개는 공백일 수 없습니다.")
    @Size(min = 1, max = 30)
    private String introduction;

    protected MemberIntroductionRequest() {
    }

    public MemberIntroductionRequest(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberIntroductionRequest that = (MemberIntroductionRequest) o;
        return Objects.equals(getIntroduction(), that.getIntroduction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntroduction());
    }

    @Override
    public String toString() {
        return "MemberIntroductionRequest{" +
                "introduction='" + introduction + '\'' +
                '}';
    }
}
