package com.rec.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class SketchS3Service {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.sketch-bucket.name}")
    private String bucketName;

    public SketchS3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadSketch(MultipartFile file) throws IOException {
        String s3Key = generateKey(file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Client.putObject(new PutObjectRequest(
                bucketName,
                s3Key,
                file.getInputStream(),
                metadata
        ));

        return s3Key;
    }

    private String generateKey(String originalName) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd/HHmmss"));
        String ext = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf('.'))
                : ".jpg";
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        return String.format("sketches/%s/%s%s", timestamp, uuid, ext);
    }
}
