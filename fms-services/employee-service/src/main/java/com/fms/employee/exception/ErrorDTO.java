/**
 * 
 */
package com.fms.employee.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
@AllArgsConstructor
public class ErrorDTO {

	HttpStatus status;
	String errMsg;
}
