/**
 * 
 */
package com.fms.employee.bean;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

/**
 * @author Kesavalu
 *
 */
@Data
@Builder
public class EmployeeRoleRequest {

	private Long employeeId;
	private String employeeName;
	private Set<Long> roleIds;
	private Set<String> roles;
		
}
