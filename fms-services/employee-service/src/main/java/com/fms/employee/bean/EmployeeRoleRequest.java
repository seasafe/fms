/**
 * 
 */
package com.fms.employee.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
@Builder
public class EmployeeRoleRequest {

	private String email;
	private String role;
	private boolean isRemove;
		
}
