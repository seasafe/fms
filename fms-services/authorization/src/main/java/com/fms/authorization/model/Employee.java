/**
 * 
 */
package com.fms.authorization.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kesavalu
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private Long employeeId;
	private String employeeName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private int isActive;
	private int isDeleted;
	
    private Set<Role> roles;
	
}
