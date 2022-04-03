package project.myblog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {
    @GetMapping("/api/error")
    public void error(HttpServletRequest request) throws AuthenticationException {
        String message = (String) request.getAttribute("message");
        String exception = (String) request.getAttribute("exception");

        if ("AuthenticationException".equals(exception)) {
            throw new AuthenticationException(message);
        }
    }
}
