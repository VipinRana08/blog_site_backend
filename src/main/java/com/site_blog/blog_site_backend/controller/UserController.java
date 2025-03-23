package com.site_blog.blog_site_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.site_blog.blog_site_backend.model.User;
import com.site_blog.blog_site_backend.repository.UserRepository;
import com.site_blog.blog_site_backend.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository repository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUser = auth.getName();
        User userInDb = userService.findByUserName(authUser);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<User> getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUser = auth.getName();
        User userInDb = userService.findByUserName(authUser);
        return new ResponseEntity<>(userInDb, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        repository.deleteUserByUserName(auth.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
