package com.rec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rec.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsernameAndPassword(String username, String password);
	
	boolean existsByUsername(String username);
	
	User findByUsername(String username);
	
	void deleteById(Integer id);
	
	
}
