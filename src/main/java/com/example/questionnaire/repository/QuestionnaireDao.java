package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{
	
//	public Questionnaire findTopByOrderById();
	
	public List<Questionnaire> findByIdIn(List<Integer> idList);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String title, LocalDate start,LocalDate end);

	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(String title, LocalDate start,LocalDate end);
	
	@Modifying
	@Transactional
	//��Ʈw������  �ĤG��Param�᭱���A����
	@Query(value = "insert into questionnaires (title, description, is_published, start_date, end_date)" 
	+ " values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public int insertData(
			String title,
			String description,
			 boolean isPublished, 
			LocalDate startDate,
			LocalDate endDate);
	
	//========================update
	@Modifying  
	@Transactional 
	@Query(value = "update  questionnaires set title = :title, description = :desp"
	+ " where id = :id", nativeQuery = true)
	public int update(
			@Param("id")int id, 
			@Param("title")String title,
			@Param("desp")String description);
	
	/*
	 * 
	 * ���gnativeQuery���P�� nativeQuery = true \n �S�Ϋe���ݩʭn��ENTITY�@�˦W
	 * �y�k�����W���ܦ�entity �� class�W�� �n�ܦ��ݩʦW��
	 */
	@Modifying  
	@Transactional 
	@Query(value = "update  Questionnaire set title = :title, description = :desp, startDate  = :startDate"  
	+ " where id = :id")
	public int updateDate(
			@Param("id")int id, 
			@Param("title")String title,
			@Param("desp")String description,
			@Param("startDate") LocalDate startDate);
	
	
	
	@Query(value = "select * from questionnaires" + " where start_date > :startDate" , nativeQuery = true)
	public List<Questionnaire> findByStartDate(@Param("startDate") LocalDate startDate);
	//nativeQuery = true , select�����n�ϥΫغc�覡(new Questionnaire)�Bentity���]�n���������غc��k
	@Query(value = "select new Questionnaire(id, title, published, description, startDate, endDate)" 
	+ "from Questionnaire" + " where startDate > :startDate")
	public List<Questionnaire> findByStartDate1(@Param("startDate") LocalDate startDate);
	
	//�ϥΧO�W,�y�k as �O�W
	@Query(value = "select qu from Questionnaire as qu " 
			+ " where startDate > :startDate or published = :isPublished")
			public List<Questionnaire> findByStartDate3(
					@Param("startDate") LocalDate startDate,
					@Param("isPublished")boolean published);
			//orderby & limit
	@Query(value = "select * from questionnaires as qu " 
			+ " where start_Date > :startDate or is_published = :isPublished order by id  desc limit :num", 
			nativeQuery = true)
			public List<Questionnaire> findByStartDate4(
					@Param("startDate") LocalDate startDate,
					@Param("isPublished")boolean published,
					@Param("num")int limitnum
					);
	
	
	@Query(value = "select * from questionnaires " 
			+ " limit :startIndex, :limitNum", 
			nativeQuery = true)
	public List<Questionnaire> findWithLimitAndStartIndex(
			@Param("startIndex") int startIndex, 
			@Param("limitNum") int limitNum
			);
	//like

	@Query(value = "select * from questionnaires " 
			+ " where title like %:title%", 
			nativeQuery = true)
	public List<Questionnaire> searchTitleLike(
			@Param("title") String title);
	//regexp
	@Query(value = "select * from questionnaires " 
			+ " where title   regexp :title", 
			nativeQuery = true)
	public List<Questionnaire> searchTitleLike2(
			@Param("title") String title);
	
	//regexp or
		@Query(value = "select * from questionnaires " 
				+ " where description   regexp :keywoed1|:keywoed2", 
				nativeQuery = true)
		public List<Questionnaire> searchDescriptionContaining(
				@Param("keywoed1") String keywoed1,
				@Param("keywoed2") String keywoed2);

}
