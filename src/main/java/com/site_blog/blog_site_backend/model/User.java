package com.site_blog.blog_site_backend.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;


@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;
    
    @Indexed(unique = true)
    @NonNull
    private String userName;
    
    private String fullName;
    @NonNull
    // @JsonIgnore
    private String password;
    @DBRef
    List<PostEntry> postEntries = new ArrayList<>();
    private List<String> roles;
}
