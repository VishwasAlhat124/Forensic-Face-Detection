package com.rec.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadCriminalImageRequest {
	private MultipartFile file;
	private String personId;
	private String username;
	private String createdAt;
	private String personName;

	// Constructors, getters, setters
	public UploadCriminalImageRequest() {
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}


