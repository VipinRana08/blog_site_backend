package com.site_blog.blog_site_backend.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Document(collection = "post_entries")
@Getter
@Setter
public class PostEntry {
    
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    @Indexed(unique = true)
    @NonNull
    private String slug;
    private String content;
    private String image;
    private String status;
    private LocalDateTime date;

    
}
