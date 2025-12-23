package com.rec.controller;

import org.springframework.beans.factory.annotation.Value;
//import com.rec.service.PdfReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rec.dto.FaceMatchDto;
import com.rec.dto.FaceSearchResponse;
import com.rec.service.FaceSearchService;
import com.rec.service.PdfReportService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/facial-recognition")
public class FaceRecognitionController {

	private final FaceSearchService faceSearchService;

	private final PdfReportService pdfReportService;

	@Value("${aws.s3.sketch-bucket.name}")
	private String sketchBucket;

	@Value("${aws.s3.region}")
	private String region;

	public FaceRecognitionController(FaceSearchService faceSearchService, PdfReportService pdfReportService) {
		this.faceSearchService = faceSearchService;
		this.pdfReportService = pdfReportService;
	}

	@GetMapping("/match-sketch")
	public ResponseEntity<FaceSearchResponse> matchSketch(@RequestParam("s3Key") String sketchS3Key) {
		FaceSearchResponse response = faceSearchService.findBestAndAllMatches(sketchS3Key);
		return ResponseEntity.ok(response);
	}

	// sketchS3Key sent from FE so we can build sketch image URL
	@PostMapping("/generate-report-pdf")
	public ResponseEntity<byte[]> generateReportPdf(@RequestParam("sketchKey") String sketchKey,
			@RequestBody FaceMatchDto bestMatch) {
		try {
			String sketchUrl = "https://" + sketchBucket + ".s3." + region + ".amazonaws.com/" + sketchKey;

			byte[] pdfBytes = pdfReportService.generateMatchReport(bestMatch, sketchUrl);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment",
					"match_report_" + (bestMatch.getPersonId() != null ? bestMatch.getPersonId() : "match") + ".pdf");

			return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
