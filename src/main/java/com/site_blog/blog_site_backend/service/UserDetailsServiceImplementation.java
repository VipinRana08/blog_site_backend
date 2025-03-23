package com.site_blog.blog_site_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.site_blog.blog_site_backend.model.User;
import com.site_blog.blog_site_backend.repository.UserRepository;

import lombok.NonNull;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{

    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username);
        if(user != null){
            return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUserName())
            .password(user.getPassword())
            .roles(user.getRoles().toArray(new String[0]))
            .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
    
}
