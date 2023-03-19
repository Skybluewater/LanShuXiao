package com.bitmotel.lanshuxiao.interceptor;

import com.bitmotel.lanshuxiao.annotation.LogoutRequired;
import com.bitmotel.lanshuxiao.exception.PermissionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LogoutAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LogoutRequired permission = handlerMethod.getMethodAnnotation(LogoutRequired.class);

        if (permission == null) {
            permission = handlerMethod.getBeanType().getAnnotation(LogoutRequired.class);
        }
        if (permission == null) {
            return true;
        }
        if (permission.required() == Boolean.FALSE) {
            return true;
        }
        // Check if user has login
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            throw new PermissionException("Logout to continue");
        }
        return true;
    }
}
