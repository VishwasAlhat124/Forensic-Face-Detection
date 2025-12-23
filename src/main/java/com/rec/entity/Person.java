package com.rec.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String notes;
	
	
	@CreationTimestamp
    private LocalDateTime createdAt;
	
	
	// New fields
    private String gender;                 // e.g. "Male", "Female", "Other"

    private Double height;                 // e.g. height in centimeters

    @Column(name = "identification_marks", columnDefinition = "TEXT")
    private String identificationMarks;    // scars, tattoos, etc.

    private LocalDate dateOfBirth; 
	

	public Person() {
		super();
	}
	
	

	public Person(Long id, String name, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.notes = notes;
	}



	public Person(String name, String notes, LocalDateTime createdAt) {
		super();
		this.name = name;
		this.notes = notes;

		this.createdAt = createdAt;
	}

	public Person(long id, String name, String notes, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.notes = notes;
		this.createdAt = createdAt;
	}
	
	
	

	public Person(Long id, String name, String notes, LocalDateTime createdAt, String gender, Double height,
			String identificationMarks, LocalDate dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.notes = notes;
		this.createdAt = createdAt;
		this.gender = gender;
		this.height = height;
		this.identificationMarks = identificationMarks;
		this.dateOfBirth = dateOfBirth;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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



	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
}
