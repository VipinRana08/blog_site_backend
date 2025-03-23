package com.site_blog.blog_site_backend.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.site_blog.blog_site_backend.model.PostEntry;
import com.site_blog.blog_site_backend.model.User;
import com.site_blog.blog_site_backend.repository.PostEntryRepository;

@Service
public class PostEntryService {

    @Autowired
    private PostEntryRepository repository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(PostEntry entry, String email){
        try {
            User user = userService.findByUserName(email);
            PostEntry saveEntry = repository.save(entry);
            user.getPostEntries().add(saveEntry);
            userService.saveOldUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Encounter error in save transaction: " + e);
        }
    }

    public void saveEntry(PostEntry entry){
        repository.save(entry);
    }
    
    public List<PostEntry> getAll(){
        return repository.findAll();
    }
    
    public Optional<PostEntry> findById(ObjectId id){
        return repository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String email){
        boolean removed = false;
        try {
            User user = userService.findByUserName(email);
            removed = user.getPostEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveOldUser(user);
                repository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deletion: " , e);
        }
        return removed;
    }
}
