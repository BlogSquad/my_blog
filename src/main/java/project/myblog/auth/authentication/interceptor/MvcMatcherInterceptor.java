package project.myblog.auth.authentication.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MvcMatcherInterceptor implements HandlerInterceptor {
    private final HandlerInterceptor handlerInterceptor;

    public MvcMatcherInterceptor(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getServletPath().contains("/posts") && request.getMethod().equals(HttpMethod.GET.name())) {
            return true;
        }

        return handlerInterceptor.preHandle(request, response, handler);
    }
}
