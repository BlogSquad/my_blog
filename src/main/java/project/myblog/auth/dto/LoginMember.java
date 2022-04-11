package project.myblog.auth.dto;

import project.myblog.domain.Member;

public class LoginMember {
    private String email;

    public LoginMember(Member member) {
        this.email = member.getEmail();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "LoginMember{" +
                "email='" + email + '\'' +
                '}';
    }
}
