/**
 * 
 */
package com.fms.employee.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.employee.model.Role;
import com.fms.employee.repository.RoleRepository;

/**
 * @author Kesavalu
 *
 */
@Service
@Transactional
public class RoleService {
	
	@Autowired
	RoleRepository roleRepo;

	public Long saveRole(Role role) {
		return roleRepo.save(role).getRoleId();
	}
}
