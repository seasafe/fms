/**
 * 
 */
package com.fms.event.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fms.event.bean.EventRequest;
import com.fms.event.model.Event;

/**
 * @author Kesavalu
 *
 */
public class CustomEventSpecification {

    public static Specification<Event> findByCriteria(final EventRequest eventRequest) {
    	return new Specification<Event>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


                List<Predicate> predicates = new ArrayList<Predicate>();

                if (eventRequest.getEventId() != null && eventRequest.getEventId()!=0) {
                    predicates.add(criteriaBuilder.equal(root.get("eventId"), eventRequest.getEventId()));
                }
                if (eventRequest.getEventRefId() != null && !eventRequest.getEventRefId().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("eventRefId")), 
                            "%" + eventRequest.getEventRefId().toLowerCase() + "%"));
                }
                if (eventRequest.getEventName() != null && !eventRequest.getEventName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("eventName")), 
                            "%" + eventRequest.getEventName().toLowerCase() + "%"));
                }
               if (eventRequest.getEventDesc() != null && !eventRequest.getEventDesc().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("eventDesc")), 
                            "%" + eventRequest.getEventDesc().toLowerCase() + "%"));
                }
                if (eventRequest.getEventDate() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Date>get("eventDate"), eventRequest.getEventDate()));
                }
                               return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
            			}

        };
    }
}
