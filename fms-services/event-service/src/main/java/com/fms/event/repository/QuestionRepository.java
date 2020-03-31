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

import com.fms.event.model.Question;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>,JpaSpecificationExecutor<Question>{

	@Query("from Question q left join fetch q.feedbackType left join fetch q.answers where q.feedbackType.type =:feedbackType")
	Optional<List<Question>> findAllByFeedBackType(String feedbackType);
	
	Page<Question> findAll(@SuppressWarnings("rawtypes") Specification spec,Pageable page);
	
}
