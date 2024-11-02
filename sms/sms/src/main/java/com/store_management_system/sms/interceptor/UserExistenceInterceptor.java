package com.store_management_system.sms.interceptor;

import com.store_management_system.sms.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserExistenceInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    public UserExistenceInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;

        if (username != null && userRepository.findByUsername(username) == null) {
            response.sendRedirect("/login?error=User does not exist. Please log in again.");
            return false;
        }

        return true; // Allow the request to proceed if the user exists
    }
}