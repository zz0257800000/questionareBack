package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;



//從前端來請求會有 一個問卷跟多個題目
public class QuizVo {
	
	
							//名字接到postman
	private Questionnaire questionnaire = new Questionnaire();
							//名字接到postman
	private List<Question> questionList = new ArrayList<>();

	private List<User> userList = new ArrayList<>(); 

	public QuizVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public QuizVo(Questionnaire questionnaire) {
		super();
		this.questionnaire = questionnaire;
	}


	public QuizVo(Questionnaire questionnaire, List<Question> questionList) {
		super();
		this.questionnaire = questionnaire;
		this.questionList = questionList;
	}
	
	

	public QuizVo(Questionnaire questionnaire, List<Question> questionList, List<User> userList) {
		super();
		this.questionnaire = questionnaire;
		this.questionList = questionList;
		this.userList = userList;
	}


	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}


	public QuizVo(List<User> userList) {
		super();
		this.userList = userList;
	}


	public List<User> getUserList() {
		return userList;
	}


	public void setUserList(List<User> userList) {
		this.userList = userList;
	}


	
	
	
	
	
}
