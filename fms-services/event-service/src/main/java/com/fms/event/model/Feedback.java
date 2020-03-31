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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Kesavalu
 *
 */

@Data
@Entity
@Table(name="t_feedback")
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class Feedback extends Audit implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="feedback_id")
	private Long feedbackId;
	
	@Column(name = "answer")
	private @NonNull String answer;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
}
