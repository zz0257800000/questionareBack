package com.example.questionnaire;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;


@SpringBootTest
public class QuizServiceTest {

	@Autowired
	private QuestionnaireDao questionnaireDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private QuizService quizService;

	@Test
	public void CreateTest1() {
		Questionnaire questionnaire = new Questionnaire();
		ArrayList questionList = new ArrayList<>();

		///////////////////
		// qn：title錯誤、說明錯誤、開始時間、結束時間、開始>結束
		QuizRes result = quizService.create(new QuizReq(questionnaire));

		// 正確新增
		questionnaire = new Questionnaire("今天吃甚麼", true, "不知道能吃", LocalDate.of(2023, 12, 19), LocalDate.of(2023, 12, 25));
		for (int i = 1; i <= 5; i++) {
			Object qu = new Question(i, 1, "QuTitle" + i, "Text" + i, "Option" + i);
			questionList.add(qu);
		}
		result = quizService.create(new QuizReq(questionnaire, questionList));
		System.out.println(result.getRtnCode());
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "create error");
//		// qn 錯誤
//		// qn：title錯誤
//		questionnaire = new Questionnaire("", true, "ABCDE", LocalDate.of(2023, 11, 19), LocalDate.of(2023, 12, 1));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "title error");
//		// qn 說明錯誤
//		questionnaire = new Questionnaire("Quiz", true, "", LocalDate.of(2023, 11, 19), LocalDate.of(2023, 12, 1));
//		result = quizService.create(new QuizReq(questionnaire));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "Description error");
//		// qn：開始時間錯誤
//		questionnaire = new Questionnaire("Quiz", true, "ABCDE", null, LocalDate.of(2023, 12, 1));
//		result = quizService.create(new QuizReq(questionnaire));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "start error");
//		// qn：結束時間錯誤
//		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 11, 19), null);
//		result = quizService.create(new QuizReq(questionnaire));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "end error");
//		// qn：開始>結束
//		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 12, 19),
//				LocalDate.of(2023, 11, 19));
//		result = quizService.create(new QuizReq(questionnaire));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "s>e error");
//		///////////////////
		// 可以只新增一張問卷沒有題目，不能只有題目沒有問卷
		// 新增一張問卷
//		questionnaire = new Questionnaire("Quiz", true, "ABCDE", LocalDate.of(2023, 11, 19),
//				LocalDate.of(2023, 11, 20));
//		// qu 錯誤
//		List<Question> questionList = new ArrayList<>();
//		Question qu = new Question();
//		// qu：id、qtitle、選項類型、選項說明
//		// id錯誤
//		qu = new Question(0, 1, "qtitle", "radio", "option1;option2;option3");
//		questionList.add(qu);
//		result = quizService.create(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id error");
//		// qtitle 錯誤
//		qu = new Question(1, 1, "", "radio", "option1;option2;option3");
//		questionList.add(qu);
//		result = quizService.create(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qtitle error");
//
//		// options_type 錯誤
//		qu = new Question(1, 1, "qtitle", "", "option1;option2;option3");
//		questionList.add(qu);
//		result = quizService.create(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "options_type error");
//
//		// options錯誤
//		qu = new Question(1, 1, "qtitle", "radio", "");
//		questionList.add(qu);
//		result = quizService.create(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "options error");

		///////////////////

	

	}

	@Test
	public void updateTest() {
	    // 创建一个新的问卷和问题列表
	    Questionnaire questionnaire = new Questionnaire();
	    List<Question> questionList = new ArrayList<>();

	    // 使用初始问卷和问题列表调用 createOrUpdate 方法
	    QuizRes result = quizService.createOrUpdate(new QuizReq(questionnaire, questionList));

	    // 断言初始 createOrUpdate 调用是否成功
	    Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "初始 createOrUpdate 错误");

	    // 更新问卷的属性
	    questionnaire.setId(29);
	    questionnaire.setTitle("7777");
	    questionnaire.setPublished(false);
	    questionnaire.setDescription("不知道要吃什麼");
	    questionnaire.setStartDate(LocalDate.of(2023, 12, 30));
	    questionnaire.setEndDate(LocalDate.of(2024, 1, 10));

	    // 更新问题列表的属性
	    List<Question> newQuestionList = Arrays.asList(
	            new Question(1, 30, "早餐", "radio", "漢堡;三明治;饅頭"),
	            new Question(2, 30, "午餐", "checkbox", "大腸麵線;老同學;一品屋"),
	            new Question(3, 30, "晚餐", "text", "")
	    );

	    // 使用更新后的问卷和问题列表再次调用 createOrUpdate 方法
	    result = quizService.createOrUpdate(new QuizReq(questionnaire, newQuestionList));

	    // 断言第二次 createOrUpdate 调用是否成功
	    Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "第二次 createOrUpdate 错误");

	    System.out.println(result.getRtnCode());
	
		// 錯誤
		// 能改的時機 ： 未發布+已發布尚未開始 ( 當前時間 < 開始時間 )
		// 已發布已開始不能改
		// qn：id、title錯誤、說明錯誤、開始時間、結束時間、開始>結束
		// qu：id、quid == id 、 qtitle、選項類型、選項說明

		//////////////////////

		// 已發布已開始

//		questionnaire = new Questionnaire(28, "publishedTest", true, "publishedAndStarted", LocalDate.of(2023, 11, 10),
//				LocalDate.of(2024, 1, 10));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "Date error");
//		//////////////////////

//		// qn：id <=0 || 沒這個ID
//		questionnaire = new Questionnaire(0, "idText", true, "idText", LocalDate.of(2023, 11, 20),
//				LocalDate.of(2024, 1, 10));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id error");
//
//		// qn id not fount
//		questionnaire = new Questionnaire(50, "idText", true, "idText", LocalDate.of(2023, 11, 20),
//				LocalDate.of(2024, 1, 10));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");
//		// qn title 錯誤
//		questionnaire = new Questionnaire(30, "", true, "TitleText", LocalDate.of(2023, 11, 20),
//				LocalDate.of(2024, 1, 10));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "title error");
//
//		// qn 說明錯誤
//		questionnaire = new Questionnaire(30, "descriptionTest", true, "", LocalDate.of(2023, 11, 20),
//				LocalDate.of(2024, 1, 10));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "description error");
//
//		// qn 開始時間
//		questionnaire = new Questionnaire(30, "startTest", true, "startTestdesc", null, LocalDate.of(2024, 1, 10));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "startDate error");
//		// qn 結束時間
//		questionnaire = new Questionnaire(30, "endTest", true, "endTestdesc", LocalDate.of(2023, 11, 20), null);
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "endDate error");
//
//		// qn 開始時間>結束時間
//		questionnaire = new Questionnaire(30, "DateTest", true, "DateTestDesc", LocalDate.of(2025, 11, 10),
//				LocalDate.of(2024, 1, 10));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "endDate error");
//
//		//////////////////////
//		// qu id 1.id<=0 2.id不存在
//		// 1.id<=0
//		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
//				LocalDate.of(2024, 1, 10));
//		Question question1 = new Question(0, 25, "qtitleXX", "textX", "OPOPTIONA");
//		questionList = new ArrayList<>(Arrays.asList(question1));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu id error");
//
//		// 2.id不存在
//		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
//				LocalDate.of(2024, 1, 10));
//		question1 = new Question(15, 25, "qtitleXX", "textX", "OPOPTIONA");
//		questionList = new ArrayList<>(Arrays.asList(question1));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu id not found");
//
//		// qu qnid != id
//		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
//				LocalDate.of(2024, 1, 10));
//		question1 = new Question(1, 20, "qtitleXX", "textX", "OPOPTIONA");
//		questionList = new ArrayList<>(Arrays.asList(question1));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qnid != id error");
//
//		// qtitle 錯誤
//		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
//				LocalDate.of(2024, 1, 10));
//		question1 = new Question(1, 25, "", "textX", "OPOPTIONA");
//		questionList = new ArrayList<>(Arrays.asList(question1));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu title error");
//
//		// option_type 錯誤
//		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
//				LocalDate.of(2024, 1, 10));
//		question1 = new Question(1, 25, "qtitle", "", "OPOPTIONA");
//		questionList = new ArrayList<>(Arrays.asList(question1));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu option_type error");

		// option 錯誤
//		questionnaire = new Questionnaire(25, "questionTest", false, "QuestionIdTestDesc", LocalDate.of(2023, 11, 30),
//				LocalDate.of(2024, 1, 10));
//		question1 = new Question(1, 25, "qtitle", "qoptiontype", "");
//		questionList = new ArrayList<>(Arrays.asList(question1));
//		result = quizService.update(new QuizReq(questionnaire, questionList));
//		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "qu option error");

		//////////////////////

	

	}

	@Test
	public void deleQuestionnaireTest() {
		// 刪多張問卷 只有 尚未發布 + 尚未開放(當前日期 < 開始日期) 可刪
		// 發佈就不可刪
		// 1. 已發布未開放 2.已發布已開放
		// 給一個int 的List

		// 刪除資料庫裡沒有的
		ArrayList<Integer> deleIdList = new ArrayList<Integer>(Arrays.asList(213, 214, 3, 4, 5));
		QuizRes result = quizService.deleQuestionnaire(deleIdList);
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");
		// 刪除資料庫裡 已開放的
		deleIdList = new ArrayList<Integer>(Arrays.asList(212, 215, 219,216));
		result = quizService.deleQuestionnaire(deleIdList);
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");
	}

	@Test
	public void deleteAllQuestionnaireAllTest() {
		questionnaireDao.deleteAll();
		questionDao.deleteAll();
		userDao.deleteAll();
	}
	

	@Test
	public void deleQuestionTest() {
		// 要刪的問卷 int deleqnid 要刪的題目 List<int> delequid

		// 刪除問卷沒有的題目
		int deleQnId = 33;
		ArrayList<Integer> deleQuIdList = new ArrayList<Integer>(Arrays.asList(34, 5));
		QuizRes result = quizService.deleQuestion(deleQnId, deleQuIdList);
		System.out.println(result.getRtnCode());
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id not found");

		// 刪除的問卷已開放
		deleQnId = 5;
		deleQuIdList = new ArrayList<Integer>(Arrays.asList(1, 2));
		result = quizService.deleQuestion(deleQnId, deleQuIdList);
		System.out.println(result.getRtnCode());
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "not published");

		// 正常刪除
//		deleQnId = 25;
//		deleQuIdList = new ArrayList<Integer>(Arrays.asList(1,5));
//		result = quizService.deleQuestion(deleQnId, deleQuIdList);
//		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL,"deleQuestion error");

	}

	@Test
	public void searchTest() {
		// 搜 標題 開始時間 結束時間 可各自獨立搜
		String title;
		LocalDate start;
		LocalDate end;
		QuizRes result;
		List<QuizVo> resList;
		////////////////////////////////////////////

		// 無搜尋結果
		title = "ABC";
		start = LocalDate.of(2023, 11, 1);
		end = LocalDate.of(2024, 1, 1);
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		System.out.println("==");
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("==");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");
//		System.out.println(result.getRtnCode());

		////////////////////////////////////////////
		// 正常搜尋
		// 標題 開始時間 結束時間
		title = "u";
		start = LocalDate.of(2023, 11, 20);
		end = LocalDate.of(2024, 1, 10);
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// 標題
		title = "Quiz";
		start = null;
		end = null;
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// 標題 開始時間
		title = "Quiz";
		start = LocalDate.of(2023, 11, 30);
		end = null;
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// 標題 結束時間 都達成才會印出來
		title = "Quiz";
		start = null;
		end = LocalDate.of(2024, 1, 1);
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// 開始時間 結束時間
		title = "";
		start = LocalDate.of(2023, 11, 1);
		end = LocalDate.of(2024, 1, 1);
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

		// 無選
		title = "";
		start = null;
		end = null;
		result = quizService.searchParam(title, start, end);
		resList = result.getQuizVoList();
		for (QuizVo vo : resList) {
			System.out.println(vo.getQuestionnaire().getId());
		}
		System.out.println("");
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");

	}
	
	@Test
	public void searchQuestionnaireTest() {
		// 前台搜尋 只搜到已開放的
		QuestionnaireRes result = quizService.searchQuestionnaireList("", null, null, true);
		for (Questionnaire qu : result.getQuestionnaire()) {
			System.out.println(qu.getId());
		}
		System.out.println(result.getRtnCode());
	}

	@Test
	public void searchQuestionTest() {
		// 前台搜尋 問卷裡的所有題目 只搜到已開放的

		// 正常搜尋
		QuestionRes result = quizService.searchQuestionList(330);
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");
		for (Question qu : result.getQuestionList()) {
			System.out.println(qu.getQuId() + " " + qu.getqnId() + " " + qu.getqTitle());
		}
//		System.out.println(result.getRtnCode());

		// 搜尋未在DB裡的問卷 --> 成功搜尋，無結果顯示
		result = quizService.searchQuestionList(3);
		Assert.isTrue(result.getRtnCode() == RtnCode.SUCCESSFUL, "search error");
		for (Question qu : result.getQuestionList()) {
			System.out.println(qu.getQuId() + " " + qu.getqnId() + " " + qu.getqTitle());
		}
//		System.out.println(result.getRtnCode());

		// id輸入錯誤
		result = quizService.searchQuestionList(0);
		Assert.isTrue(result.getRtnCode() != RtnCode.SUCCESSFUL, "id error");
//		for (Question qu : result.getQuestionList()) {
//			System.out.println(qu.getQuId() + " " + qu.getqnId() + " " + qu.getqTitle());
//		}
//		System.out.println(result.getRtnCode());
	}
	// 創建到資料庫

	@Test
	public void insertTest() {
		int res = questionnaireDao.insertData("qa_01", "qn_01 test", false, LocalDate.of(2023, 11, 24),
				LocalDate.of(2024, 01, 24));

		System.out.println(res);
		

	}

	// 更改到資料庫
	@Test
	public void updateTest1() {
		int res = questionnaireDao.update(3, "蕭毓晨", "台北");
		System.out.println(res);

	}

	@Test
	public void updateTestData() {
		int res = questionnaireDao.updateDate(387, "蕭毓晨","台北", LocalDate.of(2023, 12, 22));
		System.out.println(res);

	}

	@Test
	public void selectTest() {
//		List<Questionnaire> res = questionnaireDao.findByStartDate(LocalDate.of(2023,11,24));
//		List<Questionnaire> res = questionnaireDao.findByStartDate1(LocalDate.of(2023,11,24));
//		List<Questionnaire> res = questionnaireDao.findByStartDate3(LocalDate.of(2023, 11, 8), false);
		List<Questionnaire> res = questionnaireDao.findByStartDate4(LocalDate.of(2023, 11, 8), false, 2);

		System.out.println(res.size());
	}
	//從index0開始抓 四個
	@Test
	public void limitTest() {
		List<Questionnaire> res = questionnaireDao.findWithLimitAndStartIndex(0, 4);
		res.forEach(item ->{
			System.out.println(item.getId());
		});			
	}
	
	@Test
	public void likeTest() {
		List<Questionnaire> res = questionnaireDao.findWithLimitAndStartIndex(0, 4);
		res.forEach(item ->{
			System.out.println(item.getId());
		});			
	}
	
	@Test
	public void searchDescriptionContaining() {
		List<Questionnaire> res = questionnaireDao.searchDescriptionContaining("qn", "qa");
		res.forEach(item ->{
			System.out.println(item.getDescription());
		});			
	}
	
	@Test
	public void errorTest() {
		String a = "12A";
		try {
			int b = Integer.parseInt(a);
			System.out.println(b);
		}catch (Exception e) {
			
			System.out.println(e);
		}
		
	}
	
	

	@Test
	public void showInfoTest() {
		 quizService.showInfo(112);
	}

	 @Test
	    public void showAnswerTest() {
		 quizService.showAnswer(31);
		 
	 }
	
}
