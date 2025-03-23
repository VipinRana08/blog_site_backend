package com.site_blog.blog_site_backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.site_blog.blog_site_backend.model.User;
import com.site_blog.blog_site_backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        repository.save(user);
    }
    // public String setPassword(String password){
    //     return passwordEncoder.encode(password);
    // }
    public void saveOldUser(User user){
        repository.save(user);
    }
    public List<User> getAll(){
        return repository.findAll();
    }

    public Optional<?> findById(ObjectId id){
        return repository.findById(id);
    }

    public void delelteById(ObjectId id){
        repository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }
    
    
}
