package com.example.questionnaire.Service.ifs;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.entity.UserAnswer;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;
import com.example.questionnaire.vo.UserReq;

public interface QuizService {
	//有用到的
	public QuizRes create(QuizReq req);

	public QuizRes createUserInfo(UserReq userReq);

	public QuizRes showInfo(int id);

	public List<UserAnswer> showAnswer(int quizId);
	
		public QuizRes createOrUpdate(QuizReq req);

	public QuizRes searchParam(String title, LocalDate startDate, LocalDate endDate);
	
	public QuizRes deleQuestionnaire(List<Integer> qnIdList);

	
	
	
	public QuizRes create1(QuizReq req);

	public QuizRes deleQuestion(int qnid, List<Integer> quIdList);

	

	// 前台的搜尋，只有已發布的才搜的到
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,
			boolean isPublished);

	public QuestionRes searchQuestionList(int qnId);

	//
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate);

}
