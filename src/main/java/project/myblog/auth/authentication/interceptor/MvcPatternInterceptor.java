package project.myblog.auth.authentication.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MvcPatternInterceptor implements HandlerInterceptor {
    private final HandlerInterceptor handlerInterceptor;
    private final List<MvcPattern> excludePatterns = new ArrayList<>();

    public MvcPatternInterceptor(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (excludePatterns.stream()
                .anyMatch(excludePattern ->
                        excludePattern.match(
                                HttpMethod.resolve(request.getMethod()), request.getServletPath()))) {
            return true;
        }

        return handlerInterceptor.preHandle(request, response, handler);
    }

    public HandlerInterceptor addExcludePattern(HttpMethod method, String pattern) {
        excludePatterns.add(new MvcPattern(method, pattern));
        return this;
    }

    private static class MvcPattern {
        private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

        private final HttpMethod method;
        private final String pattern;

        private MvcPattern(HttpMethod method, String pattern) {
            this.method = method;
            this.pattern = pattern;
        }

        private boolean match(HttpMethod method, String path) {
            return this.method == method && ANT_PATH_MATCHER.match(pattern, path);
        }
    }
}
