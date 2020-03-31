/**
 * 
 */
package com.fms.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fms.employee.model.Role;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	/**
	 * @param role
	 * @return
	 */
	Role findByRoleName(String role);

}
