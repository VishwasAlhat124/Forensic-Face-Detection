package com.rec.service;

import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;

import com.rec.dao.CriminalImageRepository;
import com.rec.dto.UploadCriminalImageRequest;
import com.rec.dto.UploadCriminalImageResponse;
import com.rec.entity.CriminalImage;

@Service
@Transactional
public class CriminalImageService {
    
    private final CriminalImageRepository repository;
    private final S3Service s3Service;
    
    public CriminalImageService(CriminalImageRepository repository, S3Service s3Service) {
        this.repository = repository;
        this.s3Service = s3Service;
    }
    
    public UploadCriminalImageResponse uploadCriminalImage(UploadCriminalImageRequest request) {
        try {
            // Validate inputs
            if (request.getFile() == null || request.getFile().isEmpty()) {
                return new UploadCriminalImageResponse(false, null, "No file provided");
            }
            
            if (request.getPersonId() == null || request.getPersonId().trim().isEmpty()) {
                return new UploadCriminalImageResponse(false, null, "Person ID is required");
            }
            
            // Upload to S3
            String s3Key = s3Service.uploadCriminalImage(request.getFile(), request.getPersonId());
            
            // Parse createdAt or use current time
            LocalDateTime createdAt = parseDateTime(request.getCreatedAt());
            
            // Save to database
            CriminalImage image = new CriminalImage(
                request.getPersonId(),
                request.getPersonName(),
                request.getUsername(),
                s3Key,
                createdAt,
                request.getFile().getOriginalFilename()
            );
            
            repository.save(image);
            
            return new UploadCriminalImageResponse(true, s3Key, "Image uploaded successfully");
            
        } catch (Exception e) {
            return new UploadCriminalImageResponse(false, null, 
                "Upload failed: " + e.getMessage());
        }
    }
    
    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr != null && !dateTimeStr.trim().isEmpty()) {
            try {
                return LocalDateTime.parse(dateTimeStr);
            } catch (Exception e) {
                // Fall back to current time if parsing fails
            }
        }
        return LocalDateTime.now();
    }
}
