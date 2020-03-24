/**
 * 
 */
package com.fms.event.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.event.model.Event;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>,JpaSpecificationExecutor<Event>{

	Optional<Event> findByEventName(String eventName);
	@Query("SELECT e from Event e left join fetch e.participants left join fetch e.eventPOCs where e.eventRefId=:eventRefId")
	Optional<Event> findByEventRefId(String eventRefId);
	Page<Event> findAll(@SuppressWarnings("rawtypes") Specification spec,Pageable page);
	
}
