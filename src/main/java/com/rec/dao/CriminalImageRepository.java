package com.rec.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rec.entity.CriminalImage;

@Repository
public interface CriminalImageRepository extends JpaRepository<CriminalImage, Long> {
    Optional<CriminalImage> findByS3Key(String s3Key);
    List<CriminalImage> findByPersonId(String personId);
}

