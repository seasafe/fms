/**
 * 
 */
package com.fms.event.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Kesavalu
 *
 */

@Data
@Entity
@Table(name="t_question")
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"answers"})
public class Question extends Audit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_id")
	private Long questionId;
	
	@Column(name = "question")
	private @NonNull String question;
	
	@Column(name = "question_short_desc")
	private @NonNull String questShortDesc;
	
	@ManyToOne
	@JoinColumn(name = "feedback_type")
	private FeedbackType feedbackType;
	
	@Column(name = "total_answers")
	private int totalAnswers;
	
	@Column(name = "is_free_text")
	private boolean isFreeText;
	
	@Column(name = "is_multi_ans_allowed")
	private boolean isMultiAnsAllowed;
	
	@Column(name = "is_custom_question")
	private boolean isCustomQuestion;
	
	@OneToMany(mappedBy ="question"  ,cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,orphanRemoval = true)
	private List<Answer> answers;
}
