package project.myblog.auth.oauthAuthentication.session;

import project.myblog.auth.oauthAuthentication.Logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static project.myblog.config.WebConfig.SESSION_LOGOUT_URI;

public class SessionLogout implements Logout {

    @Override
    public boolean isSupported(HttpServletRequest request, HttpServletResponse response) {
        return SESSION_LOGOUT_URI.equals(request.getRequestURI());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
