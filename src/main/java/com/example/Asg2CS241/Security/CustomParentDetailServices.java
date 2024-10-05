package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.Parent;
import com.example.Asg2CS241.Repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomParentDetailServices implements UserDetailsService {

    @Autowired
    private ParentRepository parRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Parent parent = parRepo.findByEmail(email);

        if (parent == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomParentDetails(parent);
    }
}
