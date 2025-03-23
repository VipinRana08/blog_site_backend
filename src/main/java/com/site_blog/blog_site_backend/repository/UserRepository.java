package com.site_blog.blog_site_backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.site_blog.blog_site_backend.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{
    User findByUserName(String userName);
    void deleteUserByUserName(String userName);
    
}
