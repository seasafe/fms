/**
 * 
 */
package com.fms.employee.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
}
