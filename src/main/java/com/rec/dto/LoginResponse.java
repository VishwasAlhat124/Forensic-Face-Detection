package com.rec.dto;

public class LoginResponse {
	private boolean success;
	private String username;
	private String message;

	private String role;

	public LoginResponse() {
	}

	public LoginResponse(boolean success, String username, String message) {
		this.success = success;
		this.username = username;
		this.message = message;
	}

	public LoginResponse(boolean success, String username, String message, String role) {
		this.success = success;
		this.username = username;
		this.message = message;
		this.role = role;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
