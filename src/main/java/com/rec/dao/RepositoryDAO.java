package com.rec.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rec.entity.User;

@Repository
public interface RepositoryDAO extends JpaRepository<User, Integer>{

}
