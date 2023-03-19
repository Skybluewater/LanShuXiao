package com.bitmotel.lanshuxiao.interceptor;

import com.bitmotel.lanshuxiao.annotation.LoginRequired;
import com.bitmotel.lanshuxiao.exception.PermissionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws PermissionException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired permission = handlerMethod.getMethodAnnotation(LoginRequired.class);

        if (permission == null) {
            permission = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
        }
        if (permission == null) {
            return true;
        }
        if (permission.required() == Boolean.FALSE) {
            return true;
        }
        // Check if user has login
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userID");
        if (userId == null) {
            throw new PermissionException("Login to continue");
        }
        return true;
    }
}
