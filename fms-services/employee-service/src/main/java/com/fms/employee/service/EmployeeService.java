/**
 * 
 */
package com.fms.employee.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fms.employee.model.Employee;
import com.fms.employee.model.Role;
import com.fms.employee.repository.EmployeeRepository;
import com.fms.employee.repository.RoleRepository;
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
	private RoleRepository roleRepo;
	
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



	/**
	 * @return
	 */
	public Page<Employee> getEmployeeByRole(String role,Pageable pageable) {
		return employeeRepo.findByRoles_RoleNameAndIsActiveAndIsDeleted(role,Constants.ACTIVE,Constants.PRESENT,pageable).orElseThrow(() -> new ResourceNotFoundException("PMO Users not found"));
	}



	/**
	 * @param email
	 * @param role
	 * @param isRemove
	 */
	public void assignRole(String email, String role, boolean isRemove) {
		Employee employee = employeeRepo.findByEmailAndIsActiveAndIsDeleted(email, Constants.ACTIVE,Constants.PRESENT).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		Role roleObj = roleRepo.findByRoleName(role);
		employee.getRoles().remove(roleObj);
		if(!isRemove) {
			employee.getRoles().add(roleObj);
		}
		employeeRepo.save(employee);
	}



	/**
	 * @param employeeIds
	 * @return
	 */
	public Employee[] getEmployeeList(List<Long> employeeIds) {
		List<Employee> employees = employeeRepo.findAllByEmployeeIdAndIsActiveAndIsDeleted(employeeIds, Constants.ACTIVE,Constants.PRESENT).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
		return CollectionUtils.isEmpty(employees)?null:employees.toArray(new Employee[employees.size()]);
	}
	
	

	
}
