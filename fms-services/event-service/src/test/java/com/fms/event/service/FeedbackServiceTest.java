/**
 * 
 */
package com.fms.event.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.fms.event.EventServiceApplicationTests;
import com.fms.event.dto.FeedbackDTO;
import com.fms.event.model.Employee;
import com.fms.event.model.Feedback;

/**
 * @author kesah
 *
 */
class FeedbackServiceTest extends EventServiceApplicationTests{

	
	@Autowired
	FeedbackService feedbackService;
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
	 * Test method for {@link com.fms.event.service.FeedbackService#getAllFeedbackByEvent(java.lang.String)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	@Order(4)
	void testGetAllFeedbackByEvent() {
		List<Feedback> feedbacks= feedbackService.getAllFeedbackByEvent("EVNT00047261");
		System.out.println(feedbacks);
		Assert.notEmpty(feedbacks);
	}

	/**
	 * Test method for {@link com.fms.event.service.FeedbackService#getAllFeedbackByEventName(java.lang.String)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	@Order(3)
	void testGetAllFeedbackByEventName() {
		List<Feedback> feedbacks= feedbackService.getAllFeedbackByEventName("Bags of Joy Distribution");
		System.out.println(feedbacks);
		Assert.notEmpty(feedbacks);
	}

	/**
	 * Test method for {@link com.fms.event.service.FeedbackService#getAllFeedbackByFeedbackType(java.lang.String)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	@Order(2)
	void testGetAllFeedbackByFeedbackType() {
		List<Feedback> feedbacks= feedbackService.getAllFeedbackByFeedbackType("Participated");
		System.out.println(feedbacks);
		Assert.notEmpty(feedbacks);
	}

	/**
	 * Test method for {@link com.fms.event.service.FeedbackService#saveFeedback(com.fms.event.dto.FeedbackDTO, com.fms.event.model.Employee)}.
	 */
	@Test
	@Order(1)
	void testSaveFeedback() {
		FeedbackDTO dto = new FeedbackDTO();
		dto.setEventRefId("EVNT00047261");
		dto.setAnswer("Poor");
		dto.setQuestionId(2l);
		List<FeedbackDTO> dtos= new ArrayList<>();
		dtos.add(dto);
		List<Long> feedbackId = feedbackService.saveFeedback(dtos, SecurityContextHolder.getContext().getAuthentication());
		Assert.notNull(feedbackId);
	}

}
