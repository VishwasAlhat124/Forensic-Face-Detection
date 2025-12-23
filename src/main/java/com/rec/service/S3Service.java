package com.rec.service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    
    private final AmazonS3 s3Client;
    
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    
    public S3Service(@Qualifier("customS3Client") AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }
    
    public String uploadCriminalImage(MultipartFile file, String personId) throws IOException {
        String fileName = generateS3Key(personId, file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        return fileName;
    }
    
    private String generateS3Key(String personId, String originalFileName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss"));
        String extension = originalFileName != null ? 
            originalFileName.substring(originalFileName.lastIndexOf('.')) : ".jpg";
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        
        return String.format("criminals/%s/%s/%s_%s%s", 
            personId, timestamp, personId, uuid, extension);
    }
}

