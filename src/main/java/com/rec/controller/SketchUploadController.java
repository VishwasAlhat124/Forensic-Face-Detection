package com.rec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rec.entity.SketchImage;
import com.rec.service.SketchImageService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/facial-recognition")
public class SketchUploadController {

    private final SketchImageService sketchImageService;

    public SketchUploadController(SketchImageService sketchImageService) {
        this.sketchImageService = sketchImageService;
    }

    @PostMapping(value = "/upload-sketch", consumes = "multipart/form-data")
    public ResponseEntity<SketchImage> uploadSketch(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "username", required = false) String username) {

        try {
            SketchImage saved = sketchImageService.uploadSketch(file, username);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
