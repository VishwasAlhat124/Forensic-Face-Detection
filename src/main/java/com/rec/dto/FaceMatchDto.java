package com.rec.dto;

//import java.util.List;

public class FaceMatchDto {
	private String personId;
	private float similarity;
	private String imageUrl; // optional – pre-signed S3 URL
	
	 // just what you want to show:
    private String personName;
    private String personNotes;
    
    
    private String gender;
    private Double height;
    private String identificationMarks;
    private String dateOfBirth;

	public FaceMatchDto(String personId, float similarity, String imageUrl, String personName, String personNotes) {
		super();
		this.personId = personId;
		this.similarity = similarity;
		this.imageUrl = imageUrl;
		this.personName = personName;
		this.personNotes = personNotes;
	}

	public FaceMatchDto() {
	}

	public FaceMatchDto(String personId, float similarity, String imageUrl) {
		this.personId = personId;
		this.similarity = similarity;
		this.imageUrl = imageUrl;
	}
	
	

	public FaceMatchDto(String personId, float similarity, String imageUrl, String personName, String personNotes,
			String gender, Double height, String identificationMarks, String dateOfBirth) {
		super();
		this.personId = personId;
		this.similarity = similarity;
		this.imageUrl = imageUrl;
		this.personName = personName;
		this.personNotes = personNotes;
		this.gender = gender;
		this.height = height;
		this.identificationMarks = identificationMarks;
		this.dateOfBirth = dateOfBirth;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonNotes() {
		return personNotes;
	}

	public void setPersonNotes(String personNotes) {
		this.personNotes = personNotes;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getIdentificationMarks() {
		return identificationMarks;
	}

	public void setIdentificationMarks(String identificationMarks) {
		this.identificationMarks = identificationMarks;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
	
}