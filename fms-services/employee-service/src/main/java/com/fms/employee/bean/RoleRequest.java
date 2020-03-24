package com.fms.employee.bean;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 */

/**
 * @author Kesavalu
 *
 */
@Data
@Component
public class RoleRequest {
	private String role;
	private String description;
}
