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
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Kesavalu
 *
 */

@Data
@Entity
@Table(name="t_feedback_type")
@NoArgsConstructor
@RequiredArgsConstructor
public class FeedbackType extends Audit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="feedback_type_id")
	private Long feedbackTypeId;
	
	@Column(name = "type")
	private @NonNull String type;
	

	
}
