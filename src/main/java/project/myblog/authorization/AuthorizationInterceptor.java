package project.myblog.authorization;

import org.springframework.web.servlet.HandlerInterceptor;
import project.myblog.exception.AuthorizationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getSession(false) == null) {
            request.setAttribute("message", "로그인이 필요합니다.");
            request.setAttribute("exception", AuthorizationException.class);
            try {
                request.getRequestDispatcher("/api/error").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        return true;
    }
}
