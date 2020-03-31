/**
 * 
 */
package com.fms.event.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="t_question_answer")
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"question"}) 
public class Answer extends Audit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_answer_id")
	private Long questionAnswerId;
	
	@Column(name = "answer")
	private @NonNull String answer;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	@JsonIgnore
	private Question question;
	
}
