/**
 * 
 */
package com.fms.event.batch;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.fms.event.batch.dto.EventSummaryDTO;
import com.fms.event.model.Event;
import com.fms.event.model.EventPOC;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Component
@Slf4j
public class EventSummaryProcessor implements ItemProcessor<EventSummaryDTO, Event> {

	@Override
	public Event process(EventSummaryDTO item) throws Exception {
		log.info("Itemprocess - Processing item - {}",item);
		Event event = new Event();
		event.setEventRefId(item.getEventRefId());
		event.setBaseLocation(item.getBaseLoc());
		event.setBeneficiary(item.getBeneficiaryName());
		event.setVenueAddress(item.getVenueAddress());
		event.setCouncil(item.getCouncilName());
		event.setProject(item.getProject());
		event.setCategory(item.getCategory());
		event.setEventName(item.getEventName());
		event.setEventDesc(item.getEventDesc());
		event.setEventDate(Date.from(LocalDate.parse(item.getEventDate(), DateTimeFormatter.ofPattern("dd-MM-yy")).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		event.setTotalVolunteerHours((int) Double.parseDouble(item.getTotalVolunteerHours()));
		event.setTotalVolunteers((int) Double.parseDouble(item.getTotalVolunteers()));
		event.setTotalTravelHours((int) Double.parseDouble(item.getTotalTravelHours()));
		event.setOverAllVolunteerHours((int) Double.parseDouble(item.getOverAllVolunteerHours()));
		event.setLiveImpacted((int) Double.parseDouble(item.getLivesImpacted()));
		event.setActivityType((int) Double.parseDouble(item.getActivityType()));
		event.setStatus(item.getStatus());
		Set<EventPOC> pocs = new HashSet<>();
		if(null != item.getPocId()) {
			List<Long> employeePocIds = Stream.of(item.getPocId().split(";")).map( i ->  Double.valueOf(i).longValue())
					.collect(Collectors.toList());
			List<String> employeePocNames = Stream.of(item.getPocName().split(";"))
					.collect(Collectors.toList());
			List<String> employeePocNumbers = Stream.of(item.getPocContactNumber().split(";"))
					.collect(Collectors.toList());
			for(int i = 0 ; i<employeePocIds.size();i++) {
				EventPOC poc = new EventPOC();
				poc.setEvent(event);
				poc.setEmployeeId(employeePocIds.get(i));
				poc.setEmployeeName(employeePocNames.get(i));
				poc.setContactNumber(employeePocNumbers.get(i));
				pocs.add(poc);
			}
			event.setEventPOCs(pocs);
			
			
		}
		return event;
	}

}
