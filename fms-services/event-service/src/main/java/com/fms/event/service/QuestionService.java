/**
 * 
 */
package com.fms.event.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.fms.event.dto.PageSearch;
import com.fms.event.dto.QuestionDTO;
import com.fms.event.model.FeedbackType;
import com.fms.event.model.Question;
import com.fms.event.repository.FeedbackTypeRepository;
import com.fms.event.repository.QuestionRepository;
import com.fms.event.util.MyBeanUtil;
import com.fms.event.util.QuestionSpecificationsBuilder;

/**
 * @author Kesavalu
 *
 */
@Service
@Transactional
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private FeedbackTypeRepository feedbackTypeRepo;

	/**
	 * @param pageable
	 * @return
	 */
	public Page<QuestionDTO> getAllQuestion(Pageable pageable,PageSearch<QuestionDTO> pageSearch) {
		Specification<Question> questionSpecification = null;
		Page<Question> questions =null;
		if(null != pageSearch) {
				questionSpecification = QuestionSpecificationsBuilder.build(pageSearch.getSearchCriteria(),pageSearch.isGlobalSearch());
				questions = questionRepo.findAll(questionSpecification,pageable );	
		}else {
		questions = questionRepo.findAll(pageable );
		}
		return MyBeanUtil.getPageableDTO(questions, QuestionDTO.class);
	}

	public List<QuestionDTO> getQuestionByType(String feedbackType) {
		Optional<List<Question>> questions = questionRepo.findAllByFeedBackType(feedbackType);
		List<Question> questions1 = questions.orElseThrow(() -> new ResourceNotFoundException("Questions not found for the Feedback type passed"));
		return MyBeanUtil.copyProperties(questions1, QuestionDTO.class);
	}

	public QuestionDTO saveQuestion(QuestionDTO questionDTO) {
		Question question = MyBeanUtil.copyProperties(questionDTO, Question.class);
		if (questionDTO.getType() != null) {
			question.setFeedbackType(feedbackTypeRepo.findByType(questionDTO.getType()));
		}else if (questionDTO.getFeedbackTypeId() != null) {
			question.setFeedbackType(feedbackTypeRepo.findById(questionDTO.getFeedbackTypeId()).get());
		}
		question.getAnswers().forEach(a -> a.setQuestion(question));
		Question persistedQuestion = questionRepo.save(question);
		return MyBeanUtil.copyProperties(persistedQuestion, QuestionDTO.class);

	}

	/**
	 * @return
	 */
	public List<FeedbackType> getAllFeedbackType() {
		return feedbackTypeRepo.findAll();
	}

	/**
	 * @param questionId
	 * @return
	 */
	public QuestionDTO getQuestion(Long questionId) {
		Question question = questionRepo.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Questions not found for the id passed"));
		return MyBeanUtil.copyProperties(question, QuestionDTO.class);
	}

	/**
	 * @param questionId
	 */
	public void deleteQuestion(Long questionId) {
		Question question = questionRepo.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Questions not found for the id passed"));
		questionRepo.delete(question);
	}
	
	
}
