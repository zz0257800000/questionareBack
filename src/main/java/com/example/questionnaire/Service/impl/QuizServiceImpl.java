package com.example.questionnaire.Service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.entity.UserAnswer;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.UserAnswerDao;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;
import com.example.questionnaire.vo.UserReq;

@EnableScheduling
@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuestionnaireDao qnDao;
	@Autowired
	private QuestionDao quDao;
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserAnswerDao userAnswerDao;

	@Override
	@Transactional // 當2個save都能成功save的時候才會save，只能加在public 上面
	public QuizRes create(QuizReq req) {
		// 新增問卷
		// 使用檢查方法
		List<QuizVo> quizVoList = new ArrayList<>();
		QuizRes checkResult = checkParam(req);
		if (checkResult != null) {
			return checkResult;
		}
		// qnid要等
		// 儲存後，把 QN 中最新一筆的ID拉出來，存到QU的qn_id中，
		int qnid = qnDao.save(req.getQuestionnaire()).getId();
		List<Question> quList = req.getQuestionList();
		// 可以只新增問卷，問卷內沒有題目
		if (quList.isEmpty()) {
			quizVoList.add(req);
			return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
		} else {
			quizVoList.add(req);
		}
		// 傳單個用if多個用for
		for (Question qu : quList) {
			qu.setqnId(qnid);
		}
		quDao.saveAll(req.getQuestionList());
		return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes create1(QuizReq req) {
		Questionnaire qn = req.getQuestionnaire();
		List<Question> quList = req.getQuestionList();
		int qnid = qnDao.save(qn).getId();
		for (Question qu : quList) {
			qu.setqnId(qnid);
		}
		quDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	// 把檢查方法拉出來用
	private QuizRes checkParam(QuizReq req) {
		Questionnaire qn = req.getQuestionnaire();
		if (!StringUtils.hasText(qn.getTitle()) || qn.getStartDate() == null || qn.getEndDate() == null
				|| qn.getStartDate().isAfter(qn.getEndDate())) {
			return new QuizRes(RtnCode.QNPARAM_ERROR);
		}
//		List<Question> quList = req.getQuestionList();
//		for (Question qu : quList) {
//			if (qu.getQuId() <= 0 || !StringUtils.hasText(qu.getqTitle()) || !StringUtils.hasText(qu.getOptionsType())
//					|| !StringUtils.hasText(qu.getOptions())) {
//				return new QuizRes(RtnCode.QUPARAM_ERROR);
//			}
//		}
		// 傳null代表成功，沒有錯 成功什麼都不回傳
		return null;
	}
	// 更改問卷有問題

	@Transactional
	@Override
	public QuizRes createOrUpdate(QuizReq req) {
		Questionnaire questionnaire = req.getQuestionnaire();
		List<Question> questionList = req.getQuestionList();

		// 從資料庫擷取現有的問卷
		Optional<Questionnaire> qnop = qnDao.findById(req.getQuestionnaire().getId());

		if (qnop.isPresent()) {
			Questionnaire existingQuestionnaire = qnop.get();

			// 更新現有問卷的內容
			existingQuestionnaire.setTitle(questionnaire.getTitle());
			existingQuestionnaire.setDescription(questionnaire.getDescription());
			existingQuestionnaire.setStartDate(questionnaire.getStartDate());
			existingQuestionnaire.setEndDate(questionnaire.getEndDate());
			existingQuestionnaire.setPublished(questionnaire.isPublished());
			// 根據需要更新其他欄位

			// 將更新後的問卷再次保存到資料庫
			qnDao.save(existingQuestionnaire);

			// 保存問題的列表
			// 更新问题列表

			quDao.saveAll(questionList);

			return new QuizRes(RtnCode.SUCCESSFUL);
		} else {
			// 如果找不到問卷ID，則返回錯誤碼
			return new QuizRes(RtnCode.QNID_ERROR);
		}
	}

	// 檢查問卷是否可編輯
	private boolean isEditable(Questionnaire questionnaire) {
		// 這裡你需要根據你的邏輯來判斷問卷是否可編輯
		// 例如，你可以檢查問卷是否已經結束，或者是在進行中的情況下檢查是否允許更改
		// 返回 true 表示可編輯，返回 false 表示不可編輯
		// 這裡的邏輯需要根據你的具體需求來定義
		return true;
	}

	private QuizRes checkQuid(QuizReq req) {
		if (req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.ID_ERROR);
		}
		// 判斷QuestionList裡的quid是否等於Questionnaire裡的id
		List<Question> quList = req.getQuestionList();
		for (Question qu : quList) {
			if (qu.getqnId() != req.getQuestionnaire().getId()) {
				return new QuizRes(RtnCode.ID_ERROR);
			}
		}
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	@Transactional
	@Override
	public QuizRes deleQuestionnaire(List<Integer> qnIdList) {
		// 刪除多張問卷，無論是否已發布，只要問卷開始日期尚未過期就可以刪除
		List<Questionnaire> resList = qnDao.findByIdIn(qnIdList);
		List<Integer> deleList = new ArrayList<>();
		for (Questionnaire qn : resList) {
			// 可以刪除的條件為問卷尚未過期
			if (LocalDate.now().isBefore(qn.getEndDate())) {
				deleList.add(qn.getId());
			}
		}
		if (!deleList.isEmpty()) {
			// 刪除問卷
			qnDao.deleteAllById(deleList);

			// 刪除與問卷相關聯的問題（假設 quDao 是與問卷相關聯的資料庫表）
			quDao.deleteAllByQnIdIn(deleList);

			return new QuizRes(RtnCode.SUCCESSFUL);
		} else {
			// 如果沒有符合刪除條件的問卷，返回 ID_NOTFOUNT
			return new QuizRes(RtnCode.ID_NOTFOUNT);
		}
	}

	@Transactional
	@Override
	public QuizRes deleQuestion(int qnid, List<Integer> quIdList) {
		List<Question> resList = quDao.findByQuIdInAndQnId(quIdList, qnid);
		List<Integer> idList = new ArrayList<>();
		Optional<Questionnaire> qnOp = qnDao.findById(qnid);
		if (!qnOp.get().isPublished()
				|| qnOp.get().isPublished() && LocalDate.now().isBefore(qnOp.get().getStartDate())) {

			for (Question qu : resList) {
				idList.add(qu.getQuId());
				System.out.println(qu.getQuId() + ":" + qu.getqnId());
			}
			if (!idList.isEmpty() && qnid > 0) {
				quDao.deleteAllByQuIdInAndQnId(idList, qnid);
				return new QuizRes(RtnCode.SUCCESSFUL);
			} else {
				return new QuizRes(RtnCode.ID_NOTFOUNT);
			}
		}

		return new QuizRes(RtnCode.NOT_PUBLISHED);
	}

	@Cacheable(cacheNames = "search", key = "#account", unless = "#result.rthCode.code !=200")

	@Override
	public QuizRes searchParam(String title, LocalDate startDate, LocalDate endDate) {

		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null ? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null ? endDate : LocalDate.of(2099, 12, 31);

		/*
		 * 寫成三元式 上面 if(!StringUtils.hasText(title)) { title = ""; } if(startDate ==
		 * null) { startDate = LocalDate.of(1971, 1, 1); } if(endDate == null) { endDate
		 * = LocalDate.of(2099, 12, 31); }
		 */
		// 例：先找到第1、4、9張問卷，再找1、4、9張問卷裡的題目，並配對
		// 找到1 4 9 張問卷
		List<Questionnaire> qnList = qnDao
				.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		// 取出 1 4 9 ID
		List<Integer> qnIdList = new ArrayList<>();
		for (Questionnaire qn : qnList) {
			qnIdList.add(qn.getId());
		}
		// 找到 1 4 9 張問卷裡的題目
		List<Question> quList = quDao.findAllByQnIdIn(qnIdList);
		// 配對問卷跟題目到 VO vo裡包一張問卷QN 和該問卷的多個題目list<QU>
		// for迴圈一張一張問卷，裡再for迴圈對應問卷的題目，用set放問卷題目
		// 找到的資料需要有個東西來裝，不管是問卷還是題目
		// 問卷是一題所以單個 題目是List 所以用list 去接
		List<QuizVo> quizVoList = new ArrayList<>();
		for (Questionnaire qn : qnList) {
			QuizVo vo = new QuizVo(); // 接整張問卷和題目
			vo.setQuestionnaire(qn); // 接問卷
			List<Question> quesList = new ArrayList<>();
			for (Question qu : quList) {
				// ID相符，存入對應問卷的題目
				if (qu.getqnId() == qn.getId()) {
					quesList.add(qu);
				}
			}
			vo.setQuestionList(quesList); // 接題目
			quizVoList.add(vo); // 將題目和問卷存到VO
		}
		return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,
			boolean isAll) {
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null ? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null ? endDate : LocalDate.of(2099, 12, 31);
		List<Questionnaire> qnList = new ArrayList<>();

		if (isAll == true) {
			qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(
					title, startDate, endDate);
		} else {
			qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate,
					endDate);
		}
		return new QuestionnaireRes(qnList, RtnCode.SUCCESSFUL);
	}

	@Override
	public QuestionRes searchQuestionList(int qnId) {
		if (qnId <= 0) {
			return new QuestionRes(null, RtnCode.QNPARAM_ERROR);
		}
		List<Question> quList = quDao.findAllByQnIdIn(Arrays.asList(qnId));
		return new QuestionRes(quList, RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null ? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null ? endDate : LocalDate.of(2099, 12, 31);

		/*
		 * 寫成三元式 上面 if(!StringUtils.hasText(title)) { title = ""; } if(startDate ==
		 * null) { startDate = LocalDate.of(1971, 1, 1); } if(endDate == null) { endDate
		 * = LocalDate.of(2099, 12, 31); }
		 */
		// 例：先找到第1、4、9張問卷，再找1、4、9張問卷裡的題目，並配對
		// 找到1 4 9 張問卷
		List<Questionnaire> qnList = qnDao
				.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		// 取出 1 4 9 ID
		List<Integer> qnIdList = new ArrayList<>();
		for (Questionnaire qn : qnList) {
			qnIdList.add(qn.getId());
		}
		// 找到 1 4 9 張問卷裡的題目
		List<Question> quList = quDao.findAllByQnIdIn(qnIdList);
		// 配對問卷跟題目到 VO vo裡包一張問卷QN 和該問卷的多個題目list<QU>
		// for迴圈一張一張問卷，裡再for迴圈對應問卷的題目，用set放問卷題目
		// 找到的資料需要有個東西來裝，不管是問卷還是題目
		// 問卷是一題所以單個 題目是List 所以用list 去接
		List<QuizVo> quizVoList = new ArrayList<>();
		for (Questionnaire qn : qnList) {
			QuizVo vo = new QuizVo(); // 接整張問卷和題目
			vo.setQuestionnaire(qn); // 接問卷
			List<Question> quesList = new ArrayList<>();
			for (Question qu : quList) {
				// ID相符，存入對應問卷的題目
				if (qu.getqnId() == qn.getId()) {
					quesList.add(qu);
				}
			}
			vo.setQuestionList(quesList); // 接題目
			quizVoList.add(vo); // 將題目和問卷存到VO
		}
		return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes showInfo(int id) {
		// 檢查提供的問卷 ID 是否存在
		if (!qnDao.existsById(id)) {
			return new QuizRes(RtnCode.ID_NOTFOUNT);
		}

		// 取得指定 ID 的問卷
		Optional<Questionnaire> specifiedQuestionnaire = qnDao.findById(id);

		// 以下部分與您原有的方法一致
		List<Question> questionList = quDao.findAllByQnId(id);
		for (Question q : questionList) {
			System.out.println("問題: " + q.getqTitle());
		}

		List<User> user = userDao.findAllByquizId(id);
		for (User u : user) {
			System.out.println("答案: " + u.getName());
		}

		QuizVo quizVo = new QuizVo(specifiedQuestionnaire.get(), questionList, user);
		return new QuizRes(quizVo, RtnCode.SUCCESSFUL);
	}

	@Override
	public List<UserAnswer>  showAnswer(int quizId) {
		
		// 找出对应quizId的用户答案
		List<UserAnswer> userAnswerList = userAnswerDao.findAllByQuizId(quizId);
		// 返回成功的响应，携带用户填写数据列表
		return userAnswerList;
	}

	// 填寫資料存quizId,name,phoneNumber,email
	@Transactional
	@Override
	public QuizRes createUserInfo(UserReq userReq) {

		if (userReq == null || userReq.getUser() == null || userReq.getUserAnswerList() == null) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		// 包在userReq裡面獲取User在設定DateTime
		userReq.getUser().setDateTime(LocalDateTime.now());
		// 儲存使用者到數據庫
		User user = userDao.save(userReq.getUser());
		for (UserAnswer userAnswer : userReq.getUserAnswerList()) {
			userAnswer.setUlId(user.getUserlistId());
		}
		userAnswerDao.saveAll(userReq.getUserAnswerList());

		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	

	

	

	// 秒 分 時 日 月 週
//	@Scheduled(cron = "0/5 * 14 * * * ")
//	public void schedule() {
//		
//		System.out.println(LocalDateTime.now());
//	}
//	@Scheduled(cron = "0 * 15 * * * ")
//	public void updateOnschedule() {
//		LocalDate today =LocalDate.now();
//		int res = qnDao.
//
//		System.out.println(res);
//
//		System.out.println(today);
//	}

}