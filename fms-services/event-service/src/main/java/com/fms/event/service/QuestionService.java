/**
 * 
 */
package com.fms.event.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.fms.event.dto.QuestionDTO;
import com.fms.event.model.FeedbackType;
import com.fms.event.model.Question;
import com.fms.event.repository.EventRepository;
import com.fms.event.repository.FeedbackTypeRepository;
import com.fms.event.repository.QuestionRepository;
import com.fms.event.util.MyBeanUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Kesavalu
 *
 */
@Service
@Transactional
@Slf4j
public class QuestionService {

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private FeedbackTypeRepository feedbackTypeRepo;

	/**
	 * @param pageable
	 * @return
	 */
	public List<QuestionDTO> getAllQuestion() {
		List<Question> questions = questionRepo.findAll();
		return MyBeanUtil.copyProperties(questions, QuestionDTO.class);
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
