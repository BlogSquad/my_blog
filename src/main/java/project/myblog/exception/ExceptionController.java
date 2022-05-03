package project.myblog.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController {
    @GetMapping("/api/error")
    public void error(HttpServletRequest request) throws BusinessException {
        ErrorCode exceptionType = (ErrorCode) request.getAttribute("exceptionType");
        throw new BusinessException(exceptionType);
    }
}
