package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.Student;
import com.example.Asg2CS241.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomStudentDetailsService implements UserDetailsService {
    @Autowired
    private StudentRepository stuRepo;




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = stuRepo.findByEmail(email);

        if (student == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomStudentDetails(student);



    }
}
