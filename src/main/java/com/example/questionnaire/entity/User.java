package com.example.questionnaire.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
public class User {

	@Id

	@Column(name = "userlist_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userlistId;
	
	@Column(name = "quiz_id")
	private int quizId;
	
	@Column(name = "name")
	private String name;
	
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;


	@Column(name = "date_time")
	@JsonProperty("date_time")
	private LocalDateTime dateTime;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userlistId, int quizId, String name, String phoneNumber, String email, LocalDateTime dateTime) {
		super();
		this.userlistId = userlistId;
		this.quizId = quizId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.dateTime = dateTime;
	}

	public int getUserlistId() {
		return userlistId;
	}

	public void setUserlistId(int userlistId) {
		this.userlistId = userlistId;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	

	
}
