package com.rec.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String username;
	
	private String email;
	
	private String password;
	
	private String role;
	
	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

	public User() {
		super();
	}

	public User(String username, String email, String password, String role, LocalDateTime createdAt) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
	}

	public User(int id, String username, String email, String password, String role, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
	}

	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
