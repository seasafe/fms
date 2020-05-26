/**
 * 
 */
package com.fms.event.dto;

import java.io.Serializable;
import java.util.List;

import com.fms.event.model.Answer;
import com.fms.event.model.FeedbackType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Kesavalu
 *
 */


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class QuestionDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long questionId;
	private String question;
	private String questShortDesc;
	@Setter(AccessLevel.NONE)
	private FeedbackType feedbackType;
	private Long feedbackTypeId;
	private String type;
	private int totalAnswers;
	private boolean isFreeText;
	private boolean isMultiAnsAllowed;
	private boolean isCustomQuestion;
	private List<Answer> answers;
	private String total;
	
	/**
	 * @param feedbackType the feedbackType to set
	 */
	public void setFeedbackType(FeedbackType feedbackType) {
		this.feedbackType = feedbackType;
		if(null !=feedbackType && null!=feedbackType.getFeedbackTypeId()) {
			this.feedbackTypeId = feedbackType.getFeedbackTypeId();
		}
		if(null !=feedbackType && null!=feedbackType.getType()) {
			this.type = feedbackType.getType();
		}
	}
	}
