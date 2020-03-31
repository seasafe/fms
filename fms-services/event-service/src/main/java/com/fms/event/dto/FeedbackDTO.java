/**
 * 
 */
package com.fms.event.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @author kesah
 *
 */
@Data
public class FeedbackDTO {
	private Long feedbackId;
	private @NonNull String answer;
	private Long employeeId;
	private String employeeName;
	private @NonNull String eventRefId;
	private @NonNull Long questionId;
	
}
