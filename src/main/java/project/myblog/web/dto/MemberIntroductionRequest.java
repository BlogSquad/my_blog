package project.myblog.web.dto;

import java.util.Objects;

public class MemberIntroductionRequest {
    private String introduction;

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
