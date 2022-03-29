package project.myblog.web.controller.dto;

public class RequestMember {
    private String id;
    private String name;
    private final String email;
    private String introduction;

    public RequestMember(String id, String name, String email, String introduction) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.introduction = introduction;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIntroduction() {
        return introduction;
    }
}
