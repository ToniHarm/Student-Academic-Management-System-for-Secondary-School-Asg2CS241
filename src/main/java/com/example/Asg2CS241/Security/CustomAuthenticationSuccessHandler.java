package com.example.Asg2CS241.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Get the user roles
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = null;

        // Loop through roles and set the appropriate dashboard URL
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                redirectUrl = "/CourseAdminDashboard";
                break;
            } else  if (authority.getAuthority().equals("ROLE_STUDENT")) {
                redirectUrl = "/StudentDashboard";
                break;
            } else if (authority.getAuthority().equals("ROLE_PARENT")) {
                redirectUrl = "/ParentDashboard";
                break;
            } else if (authority.getAuthority().equals("ROLE_INSTRUCTOR")) {
                redirectUrl = "/CourseInstructorDashboard";
                break;
            }
        }

        if (redirectUrl == null) {
            throw new IllegalStateException("Role not found");
        }

        // Redirect user to the appropriate dashboard
        response.sendRedirect(redirectUrl);
    }
}
