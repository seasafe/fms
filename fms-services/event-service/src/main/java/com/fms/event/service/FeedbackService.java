/**
 * 
 */
package com.fms.event.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fms.event.client.EmployeeClient;
import com.fms.event.dto.FeedbackDTO;
import com.fms.event.model.Employee;
import com.fms.event.model.Event;
import com.fms.event.model.EventParticipant;
import com.fms.event.model.Feedback;
import com.fms.event.model.Question;
import com.fms.event.repository.EventParticipantRepository;
import com.fms.event.repository.EventRepository;
import com.fms.event.repository.FeedbackRepository;
import com.fms.event.repository.QuestionRepository;
import com.fms.event.util.DateUtils;
import com.fms.event.util.MailUtil;

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
	private FeedbackRepository feedbackRepo;
	
	@Autowired 
	EmployeeClient employeeClient;
	
	@Autowired
	EventParticipantRepository eventParticipantRepository;
	
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Value("${from.address}")
	private String fromAddress;
	
	
	
	
	
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

	public List<Long> saveFeedback(List<FeedbackDTO> feedbackDtos, Authentication authentication) {
		if(!CollectionUtils.isEmpty(feedbackDtos)) {
			Event event = eventRepo.findByEventRefId(feedbackDtos.get(0).getEventRefId()).orElseThrow(() -> new ResourceNotFoundException("Feedback not found for the event"));
			List<Long> ids = new ArrayList<>();
			if(authentication != null)
			{
				String employeeName=authentication.getName();
				 final Employee employee = this.employeeClient.findByEmployeeName(employeeName);
					feedbackDtos.forEach((feedbackDto)->{
						Question question = questionRepo.findById(feedbackDto.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
						Feedback feedback = new Feedback(feedbackDto.getAnswer());
						feedback.setEmployee(employee);
						feedback.setEvent(event);
						feedback.setQuestion(question);
						ids.add(feedbackRepo.save(feedback).getFeedbackId());
					});
			}
			
			
			return ids;
		}
		return null;
	}
	
	public Feedback validateFeedback(FeedbackDTO feedbackDto, Authentication authentication) {
		if(null != feedbackDto) {
			Event event = eventRepo.findByEventRefId(feedbackDto.getEventRefId()).orElseThrow(() -> new ResourceNotFoundException("Feedback not found for the event"));
			Employee employee = null;
			if(authentication != null)
			{
				String employeeName=authentication.getName();
				 employee = this.employeeClient.findByEmployeeName(employeeName);
			}
			List<Feedback> feedbacks = feedbackRepo.findByEvent_EventIdAndEmployee_EmployeeId(event.getEventId(),employee.getEmployeeId());
			return CollectionUtils.isEmpty(feedbacks)?null:feedbacks.get(0);
		}
		return null;
	}

	/**
	 * @param eventIds
	 * @return
	 */
	public Object sendFeedbackRequest(List<Long> eventIds) {

		List<EventParticipant> participants = eventParticipantRepository.getParticipantsByEventIds(eventIds);
		List<Long> employeeIds=participants.stream().map(p ->p.getEmployeeId()).collect(Collectors.toList());
		List<Employee> employees = this.employeeClient.findEmployeeByIds(employeeIds);
		employees.forEach(emp->{
			EventParticipant participant = participants.stream().filter(p -> emp.getEmployeeId() == p.getEmployeeId()).findFirst().orElseThrow();
			Event event = eventRepo.findById(participant.getEvent().getEventId()).get();
			this.sendFeedbackRequestMail(emp.getEmail(), participant.getStatus(), event.getEventRefId(), event.getEventName(), DateUtils.getString(event.getEventDate()), emp.getEmployeeName());
			
		});
		
		return null;
	}
	
	private void sendFeedbackRequestMail(String toAddress,String type,String eventRefId,String eventName,String eventDate,String employeeName) {
		
		Map<String, String> model = new HashMap<String, String>();
		model.put("employeeName", employeeName);
		model.put("type",type);
		model.put("eventRefId", eventRefId);
		model.put("eventName", eventName);
		model.put("eventDate", eventDate);
		model.put("url", mailUtil.getBase64FeedbackUrl(model));
		
		VelocityContext context = new VelocityContext(model);
		StringWriter stringWriter = new StringWriter();
		velocityEngine.mergeTemplate("feedback-request.vm", "UTF-8", context, stringWriter);
		try {
			mailUtil.sendEmailWithAttachment(fromAddress, new String[] {toAddress}, "Feedback Request for Event-"+eventName, stringWriter.toString(), null, null);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
