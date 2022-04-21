package project.myblog.auth.dto;

import project.myblog.domain.Member;

import java.util.Objects;

public class LoginMember {
    private String email;

    public LoginMember(Member member) {
        this.email = member.getEmail();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginMember that = (LoginMember) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "LoginMember{" +
                "email='" + email + '\'' +
                '}';
    }
}
