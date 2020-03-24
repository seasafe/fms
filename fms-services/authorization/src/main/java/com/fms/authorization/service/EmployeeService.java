package com.fms.authorization.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fms.authorization.client.EmployeeClient;
import com.fms.authorization.model.Employee;
import com.fms.authorization.util.CustomAuthorityDeserializer;


/**
 * @author Kesavalu
 *
 */
@Service(value = "employeeService")
public class EmployeeService implements UserDetailsService {
	
	
	  @Autowired private BCryptPasswordEncoder bcryptEncoder;
	  
	  @Autowired
	  private EmployeeClient employeeClient;
	 
	public UserDetails loadUserByUsername(String employeeName) throws UsernameNotFoundException {
		Employee employee = employeeClient.findByEmployeeName(employeeName);
		if(employee == null){
			
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(employee.getEmployeeName(), employee.getPassword(), getAuthority(employee));
	}
	 @JsonDeserialize(using = CustomAuthorityDeserializer.class)
	private Set<SimpleGrantedAuthority> getAuthority(Employee employee) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		  employee.getRoles().forEach(role -> { authorities.add(new
		  SimpleGrantedAuthority("ROLE_"+role.getRoleName())); });
		 
		return authorities;
	}

}
