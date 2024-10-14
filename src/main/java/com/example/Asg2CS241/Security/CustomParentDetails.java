package com.example.Asg2CS241.Security;

import com.example.Asg2CS241.Entity.Parent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomParentDetails implements UserDetails {
    private Parent parent;

    public CustomParentDetails(Parent parent) {
        this.parent = parent;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_PARENT"));
    }
    public Long getId() {
        return parent.getParentid();
    }

    @Override
    public String getPassword() {
        return parent.getPassword();
    }

    @Override
    public String getUsername() {
        return parent.getEmail();
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
