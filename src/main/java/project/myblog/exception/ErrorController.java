package project.myblog.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static project.myblog.exception.ErrorCode.MEMBER_AUTHENTICATION;

@RestController
public class ErrorController {
    @GetMapping("/api/error")
    public void error(HttpServletRequest request) throws AuthenticationException {
        Class exception = (Class) request.getAttribute("exception");

        if (AuthenticationException.class.isAssignableFrom(exception)) {
            throw new AuthenticationException(MEMBER_AUTHENTICATION);
        }
    }
}
