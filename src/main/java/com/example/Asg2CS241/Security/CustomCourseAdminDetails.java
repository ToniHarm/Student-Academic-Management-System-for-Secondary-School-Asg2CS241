package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.CourseAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomCourseAdminDetails implements UserDetails {
    private CourseAdmin courseAdmin;

    public CustomCourseAdminDetails(CourseAdmin courseAdmin) {
        this.courseAdmin = courseAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return courseAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return courseAdmin.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
