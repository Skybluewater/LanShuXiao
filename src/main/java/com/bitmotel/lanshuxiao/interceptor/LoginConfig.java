package com.bitmotel.lanshuxiao.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Bean
    LoginAuthInterceptor loginAuthInterceptor() {
        return new LoginAuthInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(List.of(
                        "/user/login",
                        "/user/registry"));
    }
}
