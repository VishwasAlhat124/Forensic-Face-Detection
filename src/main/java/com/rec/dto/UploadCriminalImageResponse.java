//UploadCriminalImageResponse.java
package com.rec.dto;

public class UploadCriminalImageResponse {
	private boolean success;
	private String s3Key;
	private String message;

	public UploadCriminalImageResponse(boolean success, String s3Key, String message) {
		this.success = success;
		this.s3Key = s3Key;
		this.message = message;
	}

	// Getters and setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
