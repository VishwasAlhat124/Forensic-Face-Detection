package com.rec.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
    
    @Value("${aws.s3.access-key}")
    private String accessKey;
    
    @Value("${aws.s3.secret-key}")
    private String secretKey;
    
    @Value("${aws.s3.region}")
    private String region;
    

    @Bean
    public AmazonS3 customS3Client() {          // Bean name = "customS3Client"
        BasicAWSCredentials credentials =
                new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
    
    @Bean
    public AmazonRekognition amazonRekognition() {
        return AmazonRekognitionClientBuilder.standard()
                .withRegion(region)
                .build();
    }
}
