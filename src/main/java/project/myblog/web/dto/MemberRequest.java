package project.myblog.web.dto;

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
}
