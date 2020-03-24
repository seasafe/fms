/**
 * 
 */
package com.fms.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fms.employee.bean.RoleRequest;
import com.fms.employee.model.Role;
import com.fms.employee.service.RoleService;

/**
 * @author Kesavalu
 *
 */
@RestController
public class RoleResource {

	@Autowired
	private RoleService roleService;
	
	
	@PostMapping("/role")
	public Long getUser(@RequestBody RoleRequest request) {
		return roleService.saveRole(new Role(request.getRole(),request.getDescription()));
	}	
}
