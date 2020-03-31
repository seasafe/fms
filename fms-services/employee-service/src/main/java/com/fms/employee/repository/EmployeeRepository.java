/**
 * 
 */
package com.fms.employee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fms.employee.model.Employee;

/**
 * @author Kesavalu
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	Optional<Employee> findByEmployeeName(String username);
	Optional<Employee> findByEmployeeNameAndIsActiveAndIsDeleted(String employeeName,int isActive,int isDeleted);
	Optional<Employee> findByEmailAndIsActiveAndIsDeleted(String employeeName,int isActive,int isDeleted);
	Optional<Employee> findByEmployeeIdAndIsActiveAndIsDeleted(Long employeeName,int isActive,int isDeleted);
	Optional<Page<Employee>> findByRoles_RoleNameAndIsActiveAndIsDeleted(String roleName,int isActive,int isDeleted,Pageable pageable);
}
