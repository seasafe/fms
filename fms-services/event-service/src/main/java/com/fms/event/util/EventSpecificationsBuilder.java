/**
 * 
 */
package com.fms.event.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.fms.event.bean.EventRequest;
import com.fms.event.model.Event;

/**
 * @author Kesavalu
 *
 */
public class EventSpecificationsBuilder {
	     
	    
		public static Specification<Event> build(List<EventRequest> params) {
	        if (params.size() == 0) {
	            return null;
	        }
	 
	        List<Specification<Event>> specs = params.stream()
	          .map( ce -> CustomEventSpecification.findByCriteria(ce))
	          .collect(Collectors.toList());
	         
	        Specification<Event> result = specs.get(0);
	 
	        for (int i = 1; i < params.size(); i++) {
	            result = Specification.where(result)
	                  .and(specs.get(i));
	        }       
	        return result;
	    }
	}

