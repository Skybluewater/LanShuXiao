package com.bitmotel.lanshuxiao.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class LogoutConfig implements WebMvcConfigurer {
    @Bean
    LogoutAuthInterceptor logoutAuthInterceptor() {
        return new LogoutAuthInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logoutAuthInterceptor()).addPathPatterns(List.of(
                        "/user/login",
                        "/user/registry"));
    }
}

