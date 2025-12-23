package com.rec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rec.dto.UploadCriminalImageRequest;
import com.rec.dto.UploadCriminalImageResponse;
import com.rec.service.CriminalImageService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CriminalImageController {

	private final CriminalImageService service;

	public CriminalImageController(CriminalImageService service) {
		this.service = service;
	}

	@PostMapping(value = "/upload-criminal-image", consumes = "multipart/form-data")
	public ResponseEntity<UploadCriminalImageResponse> uploadCriminalImage(@RequestPart("file") MultipartFile file,
			@RequestPart("personId") String personId,
			@RequestPart(value = "username", required = false) String username,
			@RequestPart(value = "createdAt", required = false) String createdAt,
			@RequestPart(value = "personName", required = false) String personName) {

		UploadCriminalImageRequest request = new UploadCriminalImageRequest();
		request.setFile(file);
		request.setPersonId(personId);
		request.setUsername(username);
		request.setCreatedAt(createdAt);
		request.setPersonName(personName);

		UploadCriminalImageResponse response = service.uploadCriminalImage(request);

		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}
}
