package project.myblog.web.dto;

import project.myblog.domain.Member;

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
    public String toString() {
        return "MemberResponse{" +
                "email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
