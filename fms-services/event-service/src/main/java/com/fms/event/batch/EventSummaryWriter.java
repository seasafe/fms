/**
 * 
 */
package com.fms.event.batch;



import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import com.fms.event.model.Event;
import com.fms.event.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Component
@Slf4j
public class EventSummaryWriter implements ItemWriter<Event> {
	
	
	@Autowired
	EventRepository eventRepo;

	@Override
	public void write(List<? extends Event> items) throws Exception {
		log.info("ItemWriter items to be saved -> {}",items);
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("eventId").withMatcher("eventRefId",ExampleMatcher.GenericPropertyMatchers.ignoreCase());
		items.forEach( item ->{
			Example<Event> example = Example.of(item,matcher) ;
			
			if(!eventRepo.exists(example))
			{
				eventRepo.save(item);
			}
		});
		
	}

}
