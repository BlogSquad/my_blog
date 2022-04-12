package project.myblog.auth.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Authorization {
    boolean isSupported(HttpServletRequest request, HttpServletResponse response);
    void authorize(HttpServletRequest request, HttpServletResponse response);
}
