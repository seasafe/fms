/**
 * 
 */
package com.fms.event.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fms.event.client.EmployeeClient;
import com.fms.event.dto.FeedbackDTO;
import com.fms.event.model.Employee;
import com.fms.event.model.Event;
import com.fms.event.model.Feedback;
import com.fms.event.model.Question;
import com.fms.event.repository.EventRepository;
import com.fms.event.repository.FeedbackRepository;
import com.fms.event.repository.FeedbackTypeRepository;
import com.fms.event.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Service
@Transactional
@Slf4j
public class FeedbackService {

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private FeedbackTypeRepository feedbackTypeRepo;

	@Autowired
	private FeedbackRepository feedbackRepo;
	
	@Autowired EmployeeClient employeeClient;
	
	
	/**
	 * @param pageable
	 * @return
	 */
	public List<Feedback> getAllFeedbackByEvent(String eventRefId) {

		return feedbackRepo.findByEventRefId(eventRefId).orElseThrow(() -> new ResourceNotFoundException("Feedback not found for the event"));
	}

	public List<Feedback> getAllFeedbackByEventName(String eventName) {
		return feedbackRepo.findByEventName(eventName).orElseThrow(() -> new ResourceNotFoundException("Feedback not found for the event"));
	}
	
	public List<Feedback> getAllFeedbackByFeedbackType(String feedbackType){
		
		return feedbackRepo.findByFeedbackType(feedbackType).orElseThrow(() -> new ResourceNotFoundException("Feedback not found for the feedbacktype"));
	}

	public Long saveFeedback(FeedbackDTO feedbackDto, Authentication authentication) {
		if(null != feedbackDto) {
			Event event = eventRepo.findByEventRefId(feedbackDto.getEventRefId()).orElseThrow(() -> new ResourceNotFoundException("Feedback not found for the event"));
			Employee employee = null;
			if(authentication != null)
			{
				String employeeName=authentication.getName();
				 employee = this.employeeClient.findByEmployeeName(employeeName);
			}
			Question question = questionRepo.findById(feedbackDto.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
			Feedback feedback = new Feedback(feedbackDto.getAnswer());
			feedback.setEmployee(employee);
			feedback.setEvent(event);
			feedback.setQuestion(question);
			return feedbackRepo.save(feedback).getFeedbackId();
		}
		return null;
	}
}
