/**
 * 
 */
package com.fms.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.event.model.EventParticipant;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long>,JpaSpecificationExecutor<EventParticipant>{

	@Query("from EventParticipant ep where ep.event.eventId in :eventIds")
	List<EventParticipant> getParticipantsByEventIds(List<Long> eventIds);
	
	@Query("from EventParticipant ep where ep.event.eventRefId in :eventRefIds")
	List<EventParticipant> getParticipantsByEventRefIds(List<String> eventRefIds);
	
}
