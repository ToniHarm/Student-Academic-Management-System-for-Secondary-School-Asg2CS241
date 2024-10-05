package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.CourseAdmin;
import com.example.Asg2CS241.Repository.CourseAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomCourseAdminDetailService implements UserDetailsService {
    @Autowired
    private CourseAdminRepository courRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CourseAdmin courseAdmin = courRepo.findByEmail(email);


        if (courseAdmin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomCourseAdminDetails(courseAdmin);

    }
}
