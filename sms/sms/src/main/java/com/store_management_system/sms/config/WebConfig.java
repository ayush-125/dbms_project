package com.store_management_system.sms.config;
import com.store_management_system.sms.interceptor.UserExistenceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserExistenceInterceptor userExistenceInterceptor;

    @Autowired
    public WebConfig(UserExistenceInterceptor userExistenceInterceptor) {
        this.userExistenceInterceptor = userExistenceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userExistenceInterceptor)
                .addPathPatterns("/**") // Apply to all paths or specify specific paths as needed
                .excludePathPatterns("/login", "/error"); // Exclude login and error paths
    }
}