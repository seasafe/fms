/**
 * 
 */
package com.fms.event.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.fms.event.EventServiceApplicationTests;
import com.fms.event.dto.QuestionDTO;
import com.fms.event.model.Answer;
import com.fms.event.model.Question;

import io.jsonwebtoken.lang.Assert;

/**
 * @author kesah
 *
 */
class QuestionServiceTest extends EventServiceApplicationTests {

	
	@Autowired
	QuestionService questionService;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.fms.event.service.QuestionService#getAllQuestion(org.springframework.data.domain.Pageable)}.
	 */
	@Test
	@Order(3)
	void testGetAllQuestion() {
		Page<QuestionDTO> questions = questionService.getAllQuestion(null,null);
		System.out.println(questions);
		Assert.notNull(questions.getSize());
	}

	/**
	 * Test method for {@link com.fms.event.service.QuestionService#getQuestionByType(java.lang.String)}.
	 */
	@Order(2)
	@Test
	void testGetQuestionByType() {
		List<QuestionDTO> questions = questionService.getQuestionByType("Participated");
		System.out.println(questions);
		Assert.notNull(questions.size());
	}

	/**
	 * Test method for {@link com.fms.event.service.QuestionService#saveQuestion(com.fms.event.dto.QuestionDTO)}.
	 */
	@Test
	@Order(1)
	void testSaveQuestion() {
		int n=6;
		QuestionDTO questionDto = new QuestionDTO();
		List<Answer> answers = new ArrayList<>();
		for (int i=1;i<n;i++) {
			Answer ans = new Answer();
			ans.setAnswer("is it okay - "+i);
			answers.add(ans);
		}
		questionDto.setAnswers(answers);
		questionDto.setTotalAnswers(n);
		questionDto.setFeedbackTypeId(1l);
		questionDto.setQuestion("how was the event");
		questionDto.setQuestShortDesc("events -");
		questionDto= questionService.saveQuestion(questionDto);
		Assert.notNull(questionDto.getQuestionId());
	}
	@Test
	@Order(5)
	void testUpdateQuestion() {
		int n=2;
		QuestionDTO questionDto = new QuestionDTO();
		List<Answer> answers = new ArrayList<>();
		for (int i=1;i<n;i++) {
			Answer ans = new Answer();
			ans.setAnswer("is it okay - "+i);
			answers.add(ans);
		}
		questionDto.setAnswers(answers);
		questionDto.setTotalAnswers(n);
		questionDto.setFeedbackTypeId(1l);
		questionDto.setQuestion("how was the event - question updated");
		questionDto.setQuestShortDesc("events -");
		questionDto.setQuestionId(1l);
		questionDto= questionService.saveQuestion(questionDto);
		Assert.notNull(questionDto.getQuestionId());
	}

}
