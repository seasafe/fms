/**
 * 
 */
package com.fms.event.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.fms.event.bean.EventSearchRequest;
import com.fms.event.model.Event;
import com.fms.event.repository.EventRepository;
import com.fms.event.util.EventSpecificationsBuilder;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Kesavalu
 *
 */
@Service
@Transactional
@Slf4j
public class EventService {
	
	@Autowired
	private EventRepository eventRepo;
	
	
	/**
	 * @param pageable
	 * @return
	 */
	public Page<Event> getAllEvents(Pageable pageable) {
		
		return eventRepo.findAll(pageable);
	}
	
	public Event getEvent(String eventRefId) {
		Event event = eventRepo.findByEventRefId(eventRefId).orElseThrow(() ->new ResourceNotFoundException("Event details not found for event Id - "+eventRefId));
		event.getParticipants();
		event.getEventPOCs();
		return event;
	}

	/**
	 * @param eventSearchCriteria
	 * @param pageable
	 * @return
	 */
	public Page<Event> getEventBasedOnCriteria(EventSearchRequest eventSearchCriteria, Pageable pageable) {
		Specification<Event> eventSpecifications = null;
		if(null != eventSearchCriteria) {
			eventSpecifications = EventSpecificationsBuilder.build(eventSearchCriteria.getEventSearchCriteria());
		}
		return eventRepo.findAll(eventSpecifications, pageable);
	}
	
	
	
}
