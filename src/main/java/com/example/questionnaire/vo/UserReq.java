package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.entity.UserAnswer;

public class UserReq {

	private User user ;
	private List<UserAnswer> userAnswerList  ;
	public UserReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserReq(User user, List<UserAnswer> userAnswerList) {
		super();
		this.user = user;
		this.userAnswerList = userAnswerList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<UserAnswer> getUserAnswerList() {
		return userAnswerList;
	}
	public void setUserAnswerList(List<UserAnswer> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	
	

}
