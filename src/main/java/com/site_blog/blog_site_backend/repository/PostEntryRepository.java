package com.site_blog.blog_site_backend.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.site_blog.blog_site_backend.model.PostEntry;

public interface PostEntryRepository extends MongoRepository<PostEntry, ObjectId>{

    Optional<PostEntry> findBySlug(String slug);

}
