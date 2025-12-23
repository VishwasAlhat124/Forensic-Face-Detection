package com.rec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rec.service.FaceIndexService;

@RestController
@RequestMapping("/admin/face-index")
@CrossOrigin(origins = "http://localhost:5173")
public class FaceIndexController {

	private final FaceIndexService faceIndexService;

	public FaceIndexController(FaceIndexService faceIndexService) {
		this.faceIndexService = faceIndexService;
	}

	// Call this ONCE (or when you add many new images)
	@PostMapping("/index-all")
	public ResponseEntity<String> indexAll() {
		faceIndexService.indexAllCriminalImages();
		return ResponseEntity.ok("Indexing started/completed. Check logs for details.");
	}
}