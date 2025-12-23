// src/main/java/com/rec/service/FaceIndexService.java
package com.rec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.S3Object;
import com.rec.dao.CriminalImageRepository;
import com.rec.entity.CriminalImage;

@Service
public class FaceIndexService {

	private final AmazonRekognition rekognition;
	private final CriminalImageRepository criminalImageRepository;

	@Value("${aws.rekognition.collection-id}")
	private String collectionId;

	@Value("${aws.s3.bucket.name}")
	private String criminalBucket; // criminal-images-prod-1234

	public FaceIndexService(AmazonRekognition rekognition, CriminalImageRepository criminalImageRepository) {
		this.rekognition = rekognition;
		this.criminalImageRepository = criminalImageRepository;
	}

	public void indexAllCriminalImages() {
		List<CriminalImage> images = criminalImageRepository.findAll();

		for (CriminalImage img : images) {
			try {
				System.out.println("Indexing: bucket=" + criminalBucket + " key=" + img.getS3Key() + " personId="
						+ img.getPersonId());

				Image image = new Image()
						.withS3Object(new S3Object().withBucket(criminalBucket).withName(img.getS3Key()));

				IndexFacesRequest request = new IndexFacesRequest().withCollectionId(collectionId).withImage(image)
						.withExternalImageId(img.getPersonId()) // link Rekognition to your DB personId
						.withMaxFaces(1).withQualityFilter("AUTO");

				rekognition.indexFaces(request);

			} catch (Exception e) {
				System.err.println("Failed to index " + img.getS3Key() + " : " + e.getMessage());
			}
		}
	}
}
