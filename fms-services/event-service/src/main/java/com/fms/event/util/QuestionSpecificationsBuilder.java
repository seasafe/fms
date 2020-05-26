/**
 * 
 */
package com.fms.event.util;

import org.springframework.data.jpa.domain.Specification;

import com.fms.event.dto.QuestionDTO;
import com.fms.event.model.Question;

/**
 * @author Kesavalu
 *
 */
public class QuestionSpecificationsBuilder {

	public static Specification<Question> build(QuestionDTO param, boolean isGlobal) {
		if (param == null) {
			return null;
		}
		Specification<Question> specs = null;
		if(isGlobal)
			specs = CustomQuestionSpecification.findAllGlobalCriteria(param);
		else
		 specs = CustomQuestionSpecification.findByCriteria(param);				
		return specs;
		/*
		 * Specification<Question> result = specs.get(0);
		 * 
		 * for (int i = 1; i < params.size(); i++) { result =
		 * Specification.where(result).and(specs.get(i)); } return result;
		 */
	}
	
	
}
