package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.questionnaire.entity.UserAnswer;

public interface UserAnswerDao extends JpaRepository<UserAnswer, Integer>{
	public List<UserAnswer> findAllByQuizId(int quizId);
	public boolean existsByQuizId(int quizId);

}
