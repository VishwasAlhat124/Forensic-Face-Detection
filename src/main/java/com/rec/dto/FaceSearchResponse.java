package com.rec.dto;

import java.util.List;

public class FaceSearchResponse {
	private FaceMatchDto bestMatch;
	private List<FaceMatchDto> matches;

	public FaceSearchResponse() {
	}

	public FaceSearchResponse(FaceMatchDto bestMatch, List<FaceMatchDto> matches) {
		this.bestMatch = bestMatch;
		this.matches = matches;
	}

	public FaceMatchDto getBestMatch() {
		return bestMatch;
	}

	public void setBestMatch(FaceMatchDto bestMatch) {
		this.bestMatch = bestMatch;
	}

	public List<FaceMatchDto> getMatches() {
		return matches;
	}

	public void setMatches(List<FaceMatchDto> matches) {
		this.matches = matches;
	}
}