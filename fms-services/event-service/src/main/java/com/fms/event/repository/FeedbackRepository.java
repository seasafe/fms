/**
 * 
 */
package com.fms.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fms.event.model.Feedback;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>,JpaSpecificationExecutor<Feedback>{

	@Query("from Feedback fb left join fetch fb.event e left join fetch fb.question q left join fetch q.feedbackType fbqf where fbqf.type = :feedbackType")
	Optional<List<Feedback>> findByFeedbackType(String feedbackType);
	
	@Query("from Feedback fb left join fetch fb.event e left join fetch fb.question q left join fetch q.feedbackType fbqf where e.eventName = :eventName")
	Optional<List<Feedback>> findByEventName(String eventName);
	
	@Query("from Feedback fb left join fetch fb.event e left join fetch fb.question q left join fetch q.feedbackType fbqf where e.eventRefId = :eventRefId")
	Optional<List<Feedback>> findByEventRefId(String eventRefId);
	
	Page<Feedback> findAll(@SuppressWarnings("rawtypes") Specification spec,Pageable page);

	List<Feedback> findByEvent_EventIdAndEmployee_EmployeeId(Long eventId, Long employeeId);
	
}
