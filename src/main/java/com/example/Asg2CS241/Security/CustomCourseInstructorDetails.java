package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.CourseInstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomCourseInstructorDetails implements UserDetails {

    private CourseInstructor courseInstructor;

    public CustomCourseInstructorDetails(CourseInstructor courseInstructor) {
        this.courseInstructor = courseInstructor;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
    }
    public Long getId() {
        return courseInstructor.getCourseteacherid();  // This should correctly return the ID
    }

    @Override
    public String getPassword() {
        return courseInstructor.getPassword();
    }

    @Override
    public String getUsername() {
        return courseInstructor.getEmail();
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
