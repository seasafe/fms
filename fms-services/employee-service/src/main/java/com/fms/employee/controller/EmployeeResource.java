/**
 * 
 */
package com.fms.employee.controller;

import java.util.Collection;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.employee.bean.EmployeeRoleRequest;
import com.fms.employee.model.Employee;
import com.fms.employee.service.EmployeeService;

/**
 * @author Kesavalu
 *
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("employees-api")
public class EmployeeResource {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public Page<Employee> getAllEmployees(Pageable pageable) {
		return employeeService.getAllEmployees(pageable);
	}
	
	@GetMapping("/employees/{employeeName}")
	@PreAuthorize("permitAll()")
	public Employee getUser(@PathVariable @NotNull String employeeName) {
		return employeeService.getEmployee(employeeName);
	}	
	
	@PostMapping("/employees")
	public Employee saveEmployee(@RequestBody @Valid Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	@GetMapping("/pmos")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Employee> getEmployeeByRole(Pageable pageable){
		return employeeService.getEmployeeByRole("PMO",pageable);
		
	}
	
	@PostMapping("/assignRole")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Employee> assignRole(@RequestBody EmployeeRoleRequest empRoleRequest,Pageable pageable){
		if(null != empRoleRequest && StringUtils.isNotBlank(empRoleRequest.getEmail()) && StringUtils.isNotBlank(empRoleRequest.getRole())) {
			employeeService.assignRole(empRoleRequest.getEmail(),empRoleRequest.getRole(),empRoleRequest.isRemove());
		}
		return employeeService.getEmployeeByRole("PMO",pageable);
		
	}
	
}
