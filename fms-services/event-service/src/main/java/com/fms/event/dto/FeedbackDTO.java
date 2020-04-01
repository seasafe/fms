/**
 * 
 */
package com.fms.event.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author kesah
 *
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class FeedbackDTO {
	private Long feedbackId;
	private String answer;
	private Long employeeId;
	private String employeeName;
	private @NonNull String eventRefId;
	private Long questionId;
	private String employeeEmail;
	
}
