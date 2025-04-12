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
import java.util.Base64;


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

    // retrieve image file from GridFS using file ID
    public String getFilePreview(String fileId) throws IOException {
        GridFSFile gridFSFile = getImage(fileId);
        if (gridFSFile == null) {
            throw new IOException("File not found in GridFS");
        }

        try (InputStream inputStream = gridFsTemplate.getResource(gridFSFile).getInputStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            // Return the Base64 encoded string
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }

}