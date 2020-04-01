/**
 * 
 */
package com.fms.event.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fms.event.batch.EventSummaryBatchLauncher;
import com.fms.event.bean.EventSearchRequest;
import com.fms.event.dto.FeedbackDTO;
import com.fms.event.dto.QuestionDTO;
import com.fms.event.jackson.View;
import com.fms.event.model.Event;
import com.fms.event.model.Feedback;
import com.fms.event.model.FeedbackType;
import com.fms.event.service.EventService;
import com.fms.event.service.FeedbackService;
import com.fms.event.service.QuestionService;


/**
 * @author Kesavalu
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/events-api")
public class EventResource {

	@Autowired
	private EventService eventService;
	
	@Autowired
	EventSummaryBatchLauncher eventSummaryBatchLauncher;

	@Autowired
	QuestionService questionService;
	
	@Autowired
	FeedbackService feedbackService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/events")
	@JsonView(View.Summary.class)
	public Page<Event> getEvents(Pageable pageable) {
		return eventService.getAllEvents(pageable);
	}
	@GetMapping("/events/{eventRefId}")
	@JsonView(View.SummaryWithDetail.class)
	public Event getEvent(@PathVariable @NotNull String eventRefId) {
		return eventService.getEvent(eventRefId);
	}	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/events/search")
	@JsonView(View.Summary.class)
	public Page<Event> getEventBasedOnCriteria(@RequestBody EventSearchRequest eventSearchCriteria, Pageable pageable){
		return eventService.getEventBasedOnCriteria(eventSearchCriteria,pageable);
	}
	
	@GetMapping("/events/job/launch")
	public String launchJob() {
		try {
			eventSummaryBatchLauncher.launchEventSummaryJob("C:\\mywork\\event-summary.xlsx");
		} catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	@GetMapping("/feedback/questions")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<QuestionDTO> getQuestions(){
		return questionService.getAllQuestion();
	}
	
	@GetMapping("/feedback/questions/{questionId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public QuestionDTO getQuestions(@PathVariable("questionId") Long questionId){
		return questionService.getQuestion(questionId);
	}
	
	@DeleteMapping("/feedback/questions/{questionId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteQuestion(@PathVariable("questionId") Long questionId){
		questionService.deleteQuestion(questionId);
	}
	@GetMapping("/feedback/questionsbytype")
	public List<QuestionDTO> getQuestionsByType(@RequestParam String type){
		return questionService.getQuestionByType(type);
	}
	
	@PostMapping("/feedback/questions")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public QuestionDTO addQuestion(@RequestBody QuestionDTO questionDTO){
		return questionService.saveQuestion(questionDTO);
	}
	
	@PostMapping("/feedback")
	public List<Long> saveFeedback(@RequestBody List<FeedbackDTO> feedbackDTOs,Authentication authentication){
		return feedbackService.saveFeedback(feedbackDTOs, authentication);
	}
	
	@GetMapping("/feedback/type")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<FeedbackType> getFeedbackType(){
		return questionService.getAllFeedbackType();
	}
	@PostMapping("/feedback/validatefeedback")
	public Feedback validateFeedback(@RequestBody FeedbackDTO feedbackDTO,Authentication authentication){
		return feedbackService.validateFeedback(feedbackDTO, authentication);
	}
	
	@PostMapping("/sendfeedbackrequest")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void sendFeedbackRequest(@RequestBody List<Long> eventIds){
		feedbackService.sendFeedbackRequest(eventIds);
	}
	
}
