package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Repository.CourseInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomCourseInstructorDetailService implements UserDetailsService {
    @Autowired
    private CourseInstructorRepository courRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CourseInstructor courseInstructor = courRepo.findByEmail(email);

        if (courseInstructor == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomCourseInstructorDetails(courseInstructor);
    }
}
