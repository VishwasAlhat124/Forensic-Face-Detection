package com.rec.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rec.dao.SketchImageRepository;
import com.rec.entity.SketchImage;

@Service
@Transactional
public class SketchImageService {

    private final SketchImageRepository sketchRepo;
    private final SketchS3Service sketchS3Service;

    public SketchImageService(SketchImageRepository sketchRepo,
                              SketchS3Service sketchS3Service) {
        this.sketchRepo = sketchRepo;
        this.sketchS3Service = sketchS3Service;
    }

    public SketchImage uploadSketch(MultipartFile file, String username) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Sketch file is required");
        }

        String s3Key = sketchS3Service.uploadSketch(file);

        SketchImage sketch = new SketchImage();
        sketch.setS3Key(s3Key);
        sketch.setOriginalFileName(file.getOriginalFilename());
        sketch.setUploadedBy(username);
        sketch.setCreatedAt(LocalDateTime.now());

        return sketchRepo.save(sketch);
    }
}
