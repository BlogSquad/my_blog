package project.myblog.web.dto;

public class MemberResponse {
    private String email;

    public MemberResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "MemberResponse{" +
                "email='" + email + '\'' +
                '}';
    }
}
