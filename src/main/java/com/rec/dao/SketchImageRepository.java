package com.rec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rec.entity.SketchImage;

@Repository
public interface SketchImageRepository extends JpaRepository<SketchImage, Long> {
}