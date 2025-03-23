package com.site_blog.blog_site_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.site_blog.blog_site_backend.model.User;
import com.site_blog.blog_site_backend.service.UserDetailsServiceImplementation;
import com.site_blog.blog_site_backend.service.UserService;
import com.site_blog.blog_site_backend.utils.JwtUtil;


@RestController
@CrossOrigin
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImplementation userDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    } 
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try {
            if (user.getUserName() == null || user.getPassword() == null) {
                return new ResponseEntity<>("Invalid login credentials", HttpStatus.BAD_REQUEST);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK); 
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED); 
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
