package project.myblog.auth.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Authentication {
    boolean isSupported(HttpServletRequest request, HttpServletResponse response);
    void authorize(HttpServletRequest request, HttpServletResponse response);
}
