package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.CourseAdmin;
import com.example.Asg2CS241.Entity.CourseInstructor;
import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Entity.Student;
import com.example.Asg2CS241.Repository.CourseAdminRepository;
import com.example.Asg2CS241.Repository.CourseInstructorRepository;
import com.example.Asg2CS241.Repository.ParentRepository;
import com.example.Asg2CS241.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

@Controller
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private CourseInstructorRepository instructorRepository;

    @Autowired
    private CourseAdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails user = null;

        // Check the students table
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            user = new CustomStudentDetails(student);
        }

        // Check the parents table
        Parent parent = parentRepository.findByEmail(email);
        if (parent != null) {
            user = new CustomParentDetails(parent);
        }

        // Check the instructors table
        CourseInstructor instructor = instructorRepository.findByEmail(email);
        if (instructor != null) {
            user = new CustomCourseInstructorDetails(instructor);
        }

        // Check the admins table
        CourseAdmin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            user = new CustomCourseAdminDetails(admin);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}


