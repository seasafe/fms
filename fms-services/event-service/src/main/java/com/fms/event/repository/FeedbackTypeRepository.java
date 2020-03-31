/**
 * 
 */
package com.fms.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fms.event.model.FeedbackType;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface FeedbackTypeRepository extends JpaRepository<FeedbackType, Long>,JpaSpecificationExecutor<FeedbackType>{

	/**
	 * @param type
	 * @return
	 */
	FeedbackType findByType(String type);

	
}
