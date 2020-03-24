/**
 * 
 */
package com.fms.employee.service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fms.employee.model.Employee;
import com.fms.employee.repository.EmployeeRepository;
import com.fms.employee.util.Constants;

/**
 * @author Kesavalu
 *
 */
@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired 
	private BCryptPasswordEncoder bcryptEncoder;
	
	/**
	 * @param username
	 * @return
	 */
	public Employee getEmployee(@NotNull String employeeName) {
		final String employee = StringUtils.trim(employeeName);
		if(StringUtils.contains(employeeName, "@")) {
			return employeeRepo.findByEmailAndIsActiveAndIsDeleted(employeeName,Constants.ACTIVE,Constants.PRESENT).orElseThrow(() -> new ResourceNotFoundException("Employee with Email -  " + employee + " not found"));
		}else if(StringUtils.isNumeric(employeeName)) {
			return employeeRepo.findByEmployeeIdAndIsActiveAndIsDeleted(Long.valueOf(employeeName),Constants.ACTIVE,Constants.PRESENT).orElseThrow(() -> new ResourceNotFoundException("Employee with Id-  " + employee + " not found"));
		}
		return employeeRepo.findByEmployeeNameAndIsActiveAndIsDeleted(employeeName,Constants.ACTIVE,Constants.PRESENT).orElseThrow(() -> new ResourceNotFoundException("Employee with Name -  " + employee + " not found"));
	}



	/**
	 * @param employee
	 * @return
	 */
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employee.setPassword(bcryptEncoder.encode(employee.getPassword()));
		return employeeRepo.save(employee);
	}



	/**
	 * @param username
	 * @return
	 */
	public Page<Employee> getAllEmployees(Pageable pageable) {
		return employeeRepo.findAll(pageable);
	}
	
	

	
}
