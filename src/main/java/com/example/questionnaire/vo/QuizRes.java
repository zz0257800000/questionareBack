package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.constants.RtnCode;

//�^�ǰݨ�
public class QuizRes {
	//�s���h�h�i�ݨ��M�D��
	private List<QuizVo> quizVoList;  
	
	private QuizVo quizVo;
	
	
	private UserReq userReq;

	private RtnCode rtnCode;

	
	public QuizRes() {
		super();
	}


	public QuizRes( RtnCode rtnCode) {
		super();
		this.rtnCode = rtnCode;
	}
	public QuizRes(List<QuizVo> quizVoList, RtnCode rtnCode) {
		super();
		this.quizVoList = quizVoList;
		this.rtnCode = rtnCode;
	}
	
	public QuizRes(QuizVo quizVo, RtnCode rtnCode) {
		super();
		this.quizVo = quizVo;
		this.rtnCode = rtnCode;
	}


	public List<QuizVo> getQuizVoList() {
		return quizVoList;
	}


	public void setQuizVoList(List<QuizVo> quizVoList) {
		this.quizVoList = quizVoList;
	}


	public RtnCode getRtnCode() {
		return rtnCode;
	}


	public void setRtnCode(RtnCode rtnCode) {
		this.rtnCode = rtnCode;
	}


	public QuizVo getQuizVo() {
		return quizVo;
	}


	public void setQuizVo(QuizVo quizVo) {
		this.quizVo = quizVo;
	}


	public QuizRes(UserReq userReq, RtnCode rtnCode) {
		super();
		this.userReq = userReq;
		this.rtnCode = rtnCode;
	}


	public UserReq getUserReq() {
		return userReq;
	}


	public void setUserReq(UserReq userReq) {
		this.userReq = userReq;
	}




	

	
	
}
