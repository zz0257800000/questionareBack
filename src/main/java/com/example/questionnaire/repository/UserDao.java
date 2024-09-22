package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	public List<User> findAllByquizId(int id);
	  boolean existsByName(String name);

	    boolean existsByPhoneNumber(String phoneNumber);

	    boolean existsByEmail(String email);
	
}
