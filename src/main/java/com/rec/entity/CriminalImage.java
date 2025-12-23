package com.rec.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "criminal_images")
public class CriminalImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criminal_image_seq")
	@SequenceGenerator(name = "criminal_image_seq", sequenceName = "criminal_image_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "person_id", nullable = false)
	private String personId;

	@Column(name = "person_name")
	private String personName;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "s3_key", nullable = false)
	private String s3Key;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "file_name")
	private String fileName;

	// Constructors
	public CriminalImage() {
	}

	public CriminalImage(String personId, String personName, String username, String s3Key, LocalDateTime createdAt,
			String fileName) {
		this.personId = personId;
		this.personName = personName;
		this.username = username;
		this.s3Key = s3Key;
		this.createdAt = createdAt;
		this.fileName = fileName;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
