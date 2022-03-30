package project.myblog.web.dto;

import project.myblog.domain.Member;

public class SessionResponse {
    private String name;
    private String email;


    public SessionResponse(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }

    @Override
    public String toString() {
        return "SessionResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
