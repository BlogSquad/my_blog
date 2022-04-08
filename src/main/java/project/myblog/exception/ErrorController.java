package project.myblog.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {
    @GetMapping("/api/error")
    public void error(HttpServletRequest request) throws AuthenticationException {
        String message = (String) request.getAttribute("message");
        Class exception = (Class) request.getAttribute("exception");

        if (AuthenticationException.class.isAssignableFrom(exception)) {
            throw new AuthenticationException(message);
        }

        if (AuthorizationException.class.isAssignableFrom(exception)) {
            throw new AuthorizationException(message);
        }
    }
}
