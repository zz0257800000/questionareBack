package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
//(Table)名字要跟資料庫一樣
@Table(name = "user_answer")
public class UserAnswer {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ans_id")
	private int ansId;
	

	@Column(name = "ul_id")
	private int ulId;
	
	@Column(name = "quiz_id")
	
	private int quizId;
	
	@Column(name = "ans_number")
	private int ansNumber;
	
	@Column(name = "options_type")
	private String optionsType;
	
	@Column(name = "ans")
	private String ans;

	public UserAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAnswer(int ansId, int ulId, int quizId, int ansNumber, String optionsType, String ans) {
		super();
		this.ansId = ansId;
		this.ulId = ulId;
		this.quizId = quizId;
		this.ansNumber = ansNumber;
		this.optionsType = optionsType;
		this.ans = ans;
	}

	public int getAnsId() {
		return ansId;
	}

	public void setAnsId(int ansId) {
		this.ansId = ansId;
	}

	public int getUlId() {
		return ulId;
	}

	public void setUlId(int ulId) {
		this.ulId = ulId;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getAnsNumber() {
		return ansNumber;
	}

	public void setAnsNumber(int ansNumber) {
		this.ansNumber = ansNumber;
	}

	public String getOptionsType() {
		return optionsType;
	}

	public void setOptionsType(String optionsType) {
		this.optionsType = optionsType;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	
	
	
	
	
}
