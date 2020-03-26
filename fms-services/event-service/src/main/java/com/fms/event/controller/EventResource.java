/**
 * 
 */
package com.fms.event.controller;

import javax.validation.constraints.NotNull;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fms.event.batch.EventSummaryBatchLauncher;
import com.fms.event.bean.EventSearchRequest;
import com.fms.event.jackson.View;
import com.fms.event.model.Event;
import com.fms.event.service.EventService;

import ch.qos.logback.classic.Logger;


/**
 * @author Kesavalu
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/events-api")
public class EventResource {

	@Autowired
	private EventService eventService;
	
	@Autowired
	EventSummaryBatchLauncher eventSummaryBatchLauncher;

	
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
	
}
