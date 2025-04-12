package com.site_blog.blog_site_backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.site_blog.blog_site_backend.model.PostEntry;
import com.site_blog.blog_site_backend.model.User;
import com.site_blog.blog_site_backend.service.FileService;
import com.site_blog.blog_site_backend.service.PostEntryService;
import com.site_blog.blog_site_backend.service.UserService;

@RestController
@RequestMapping("/posts")
public class PostEntryController {
    
    @Autowired
    private PostEntryService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
    

    @PostMapping
    public ResponseEntity<PostEntry> createEntry(@RequestBody PostEntry entry){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            entry.setDate(LocalDateTime.now());
            postService.saveEntry(entry, user);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/file-upload")
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file){
        try {
            String imageFileId = fileService.storeImage(file);
            return new ResponseEntity<>(Map.of("fileId", imageFileId), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
    }

    @GetMapping("/file-preview/{fileId}")
    public ResponseEntity<String> getFilePreview(@PathVariable String fileId) {
        try {
            String base64Preview = fileService.getFilePreview(fileId);
            return new ResponseEntity<>(base64Preview, HttpStatus.OK);  
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving file preview: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping
    public ResponseEntity<?> getAllEntriesOfUser(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            User userDb = userService.findByUserName(user);
    
            if (userDb == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            List<PostEntry> allEntries = userDb.getPostEntries();
            
            if (allEntries != null && !allEntries.isEmpty()) {
                return new ResponseEntity<>(allEntries, HttpStatus.OK);
            }
            return new ResponseEntity<>("No posts found", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getBySlug(@PathVariable String slug){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        User userDb = userService.findByUserName(user);
        List<PostEntry> collect = userDb.getPostEntries().stream().filter(x -> x.getSlug().equals(slug)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<PostEntry> entry = postService.findBySlug(slug);
            if(entry.isPresent()){
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        User userDb = userService.findByUserName(user);
        List<PostEntry> collect = userDb.getPostEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<PostEntry> entry = postService.findById(id);
            if(entry.isPresent()){
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<?> updatePostEntryBySlug(@PathVariable String slug, @RequestBody PostEntry newEntry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        User userDb = userService.findByUserName(user);
        List<PostEntry> collect = userDb.getPostEntries().stream().filter(x -> x.getSlug().equals(slug)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            PostEntry oldEntry = postService.findBySlug(slug).orElse(null);
            if(oldEntry != null){
                oldEntry.setStatus(oldEntry.getStatus() == newEntry.getStatus() ? oldEntry.getStatus(): newEntry.getStatus());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setImage(newEntry.getImage() != null && !newEntry.getImage().equals("") ? newEntry.getImage() : oldEntry.getImage());
                postService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updatePostEntryById(@PathVariable ObjectId id, @RequestBody PostEntry newEntry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        User userDb = userService.findByUserName(user);
        List<PostEntry> collect = userDb.getPostEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            PostEntry oldEntry = postService.findById(id).orElse(null);
            if(oldEntry != null){
                oldEntry.setStatus(oldEntry.getStatus() == newEntry.getStatus() ? oldEntry.getStatus(): newEntry.getStatus());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                postService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deletePostBySlug(@PathVariable String slug){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        boolean removed = postService.deleteBySlug(slug, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable ObjectId id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        boolean removed = postService.deleteById(id, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
