package project.myblog.web.dto;

import project.myblog.domain.Member;

public class SessionMember {
    private String email;


    public SessionMember(Member member) {
        this.email = member.getEmail();
    }

    @Override
    public String toString() {
        return "SessionMember{" +
                "email='" + email + '\'' +
                '}';
    }
}
