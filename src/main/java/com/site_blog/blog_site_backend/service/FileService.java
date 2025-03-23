package com.site_blog.blog_site_backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import java.io.IOException;
import java.io.InputStream;


@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public String storeImage(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            // Store the file in GridFS with the original filename
            return gridFsTemplate.store(inputStream, file.getOriginalFilename()).toString();
        }catch(IOException e){
            return "Exception occured: " + e + "";
        }
    }
    // Retrieve image file from GridFS using file ID
    public GridFSFile getImage(String fileId) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
    }
}