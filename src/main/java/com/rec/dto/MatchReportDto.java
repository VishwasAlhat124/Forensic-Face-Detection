// com.rec.dto.MatchReportDto.java
package com.rec.dto;

public class MatchReportDto {
	private String sketchS3Key;
	private FaceMatchDto bestMatch;
	private java.time.LocalDateTime reportGeneratedAt;

	public MatchReportDto(String sketchS3Key, FaceMatchDto bestMatch) {
		this.sketchS3Key = sketchS3Key;
		this.bestMatch = bestMatch;
		this.reportGeneratedAt = java.time.LocalDateTime.now();
	}

	public String getSketchS3Key() {
		return sketchS3Key;
	}

	public void setSketchS3Key(String sketchS3Key) {
		this.sketchS3Key = sketchS3Key;
	}

	public FaceMatchDto getBestMatch() {
		return bestMatch;
	}

	public void setBestMatch(FaceMatchDto bestMatch) {
		this.bestMatch = bestMatch;
	}

	public java.time.LocalDateTime getReportGeneratedAt() {
		return reportGeneratedAt;
	}

	public void setReportGeneratedAt(java.time.LocalDateTime reportGeneratedAt) {
		this.reportGeneratedAt = reportGeneratedAt;
	}
}
