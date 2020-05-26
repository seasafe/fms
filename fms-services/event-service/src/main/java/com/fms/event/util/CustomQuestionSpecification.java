/**
 * 
 */
package com.fms.event.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fms.event.dto.QuestionDTO;
import com.fms.event.model.FeedbackType;
import com.fms.event.model.Question;

/**
 * @author Kesavalu
 *
 */
public class CustomQuestionSpecification {

	public static Specification<Question> findByCriteria(final QuestionDTO questionSearch) {
		return new Specification<Question>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (questionSearch.getType() != null && !questionSearch.getType().isEmpty()) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("feedbackType").get("type")),
							"%" + questionSearch.getType().toLowerCase() + "%"));
				}
				if (questionSearch.getQuestion() != null && !questionSearch.getQuestion().isEmpty()) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("question")),
							"%" + questionSearch.getQuestion().toLowerCase() + "%"));
				}
				try {
					if (questionSearch.getTotal() != null && !questionSearch.getTotal().isEmpty()) {
						predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("totalAnswers")),
								Integer.parseInt(questionSearch.getTotal())));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
			}

		};
	}

	/**
	 * @param param
	 * @return
	 */
	public static Specification<Question> findAllGlobalCriteria(QuestionDTO questionSearch) {
		return new Specification<Question>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (questionSearch.getQuestion() != null && !questionSearch.getQuestion().isEmpty()) {
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("question")),
							"%" + questionSearch.getQuestion().toLowerCase() + "%"));
				}
				try {
					if (questionSearch.getTotal() != null && !questionSearch.getTotal().isEmpty()) {
						predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("totalAnswers")),
								Integer.parseInt(questionSearch.getTotal())));
					}
				} catch (Exception e) {
				}
				if (questionSearch.getType() != null && !questionSearch.getType().isEmpty()) {
					predicates.add(criteriaBuilder.like(
							criteriaBuilder.lower(root.join("feedbackType", JoinType.LEFT).get("type")),
							"%" + questionSearch.getType().toLowerCase() + "%"));
				}
				return criteriaBuilder.or(predicates.stream().toArray(Predicate[]::new));
			}

		};
	}
}
