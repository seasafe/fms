/**
 * 
 */
package com.fms.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	Optional<Employee> findByEmployeeIdAndIsActiveAndIsDeleted(Long employeeId,int isActive,int isDeleted);
	Optional<Page<Employee>> findByRoles_RoleNameAndIsActiveAndIsDeleted(String roleName,int isActive,int isDeleted,Pageable pageable);
	@Query("from Employee e where e.employeeId in :employeeIds and e.isActive =:active and e.isDeleted= :present")
	Optional<List<Employee>> findAllByEmployeeIdAndIsActiveAndIsDeleted(List<Long> employeeIds, int active, int present);
}
