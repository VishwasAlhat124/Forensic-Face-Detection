package com.rec.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rec.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	
	boolean existsByName(String name);
	
	Person findTopByOrderByIdDesc(); // ✅ Use ID instead of createdAt
	
    Optional<Person> findById(Long id); // ✅ Already exists but explicit

}
