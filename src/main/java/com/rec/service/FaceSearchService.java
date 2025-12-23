package com.rec.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.rec.dao.CriminalImageRepository;
import com.rec.dto.FaceMatchDto;
import com.rec.dto.FaceSearchResponse;
import com.rec.entity.CriminalImage;
import com.rec.entity.Person;

@Service
public class FaceSearchService {

	private final AmazonRekognition rekognitionClient;
	private final CriminalImageRepository criminalImageRepository;
	private final PersonService personService;

	@Value("${aws.rekognition.collection-id}")
	private String collectionId;

	@Value("${aws.s3.sketch-bucket.name}")
	private String sketchBucket;

	@Value("${aws.s3.bucket.name}")
	private String criminalBucket; // criminal-images-prod-1234

	@Value("${aws.s3.region}")
	private String region; // e.g. ap-south-1

	public FaceSearchService(@Value("${aws.s3.region}") String region, CriminalImageRepository criminalImageRepository,
			PersonService personService) {

		this.rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(region).build();

		this.criminalImageRepository = criminalImageRepository;
		this.personService = personService;
		this.region = region;
	}

	public List<FaceMatch> searchMatchesForSketch(String sketchS3Key) {

		Image image = new Image().withS3Object(new S3Object().withBucket(sketchBucket).withName(sketchS3Key));

		SearchFacesByImageRequest request = new SearchFacesByImageRequest().withCollectionId(collectionId)
				.withImage(image).withFaceMatchThreshold(80F) // min similarity
				.withMaxFaces(5); // top N matches

		SearchFacesByImageResult result = rekognitionClient.searchFacesByImage(request);

		return result.getFaceMatches();
	}

//	public FaceSearchResponse findBestAndAllMatches(String sketchS3Key) {
//		List<FaceMatch> rawMatches = searchMatchesForSketch(sketchS3Key);
//
//		List<FaceMatchDto> dtos = rawMatches.stream().map(m -> {
//			String personId = m.getFace().getExternalImageId();
//
//			// 1) Find any criminal image for this personId
//			CriminalImage img = criminalImageRepository.findByPersonId(personId).stream().findFirst().orElse(null);
//
//			String imageUrl = null;
//			if (img != null && img.getS3Key() != null) {
//				imageUrl = "https://" + criminalBucket + ".s3." + region + ".amazonaws.com/" + img.getS3Key();
//				System.out.println("imageUrl = " + imageUrl);
//			}
//
//			// 2) Load Person and extract only name + notes
//			String personName = null;
//			String personNotes = null;
//
//			try {
//				Long id = Long.parseLong(personId);
//				Person p = personService.getPersonById(id); // ensure this takes Long
//				if (p != null) {
//					personName = p.getName();
//					personNotes = p.getNotes();
//					System.out
//							.println("Loaded Person id=" + p.getId() + " name=" + personName + " notes=" + personNotes);
//				} else {
//					System.out.println("No Person found for id=" + id);
//				}
//			} catch (NumberFormatException ex) {
//				System.out.println("ExternalImageId is not numeric: " + personId);
//			}
//
//			return new FaceMatchDto(personId, m.getSimilarity(), imageUrl, personName, personNotes);
//		}).collect(Collectors.toList());
//
//		FaceMatchDto best = dtos.isEmpty() ? null : dtos.get(0);
//
//		return new FaceSearchResponse(best, dtos);
//	}

	public FaceSearchResponse findBestAndAllMatches(String sketchS3Key) {
		List<FaceMatch> rawMatches = searchMatchesForSketch(sketchS3Key);

		List<FaceMatchDto> dtos = rawMatches.stream().map(m -> {
			String personId = m.getFace().getExternalImageId();

			// 1) Find any criminal image for this personId
			CriminalImage img = criminalImageRepository.findByPersonId(personId).stream().findFirst().orElse(null);

			String imageUrl = null;
			if (img != null && img.getS3Key() != null) {
				imageUrl = "https://" + criminalBucket + ".s3." + region + ".amazonaws.com/" + img.getS3Key();
				System.out.println("imageUrl = " + imageUrl);
			}

			// 2) Load Person and extract full details
			String personName = null;
			String personNotes = null;
			String gender = null;
			Double height = null;
			String identificationMarks = null;
			String dateOfBirth = null;

			try {
				Long id = Long.parseLong(personId);
				Person p = personService.getPersonById(id); // returns Person or null
				if (p != null) {
					personName = p.getName();
					personNotes = p.getNotes();
					gender = p.getGender();
					height = p.getHeight();
					identificationMarks = p.getIdentificationMarks();
					if (p.getDateOfBirth() != null) {
						dateOfBirth = p.getDateOfBirth().toString(); // yyyy-MM-dd
					}

					System.out.println("Loaded Person id=" + p.getId() + " name=" + personName + " notes=" + personNotes
							+ " gender=" + gender + " height=" + height + " marks=" + identificationMarks + " dob="
							+ dateOfBirth);
				} else {
					System.out.println("No Person found for id=" + id);
				}
			} catch (NumberFormatException ex) {
				System.out.println("ExternalImageId is not numeric: " + personId);
			}

			// 3) Build DTO with all fields
			FaceMatchDto dto = new FaceMatchDto();
			dto.setPersonId(personId);
			dto.setSimilarity(m.getSimilarity());
			dto.setImageUrl(imageUrl);
			dto.setPersonName(personName);
			dto.setPersonNotes(personNotes);
			dto.setGender(gender);
			dto.setHeight(height);
			dto.setIdentificationMarks(identificationMarks);
			dto.setDateOfBirth(dateOfBirth);

			return dto;
		}).collect(Collectors.toList());

		FaceMatchDto best = dtos.isEmpty() ? null : dtos.get(0);
		return new FaceSearchResponse(best, dtos);
	}

}
