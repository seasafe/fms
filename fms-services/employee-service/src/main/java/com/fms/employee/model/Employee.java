/**
 * 
 */
package com.fms.employee.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Kesavalu
 *
 */
@Data
@Entity
@Table(name="t_employee")
@EqualsAndHashCode(exclude="roles")
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Audit{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	@Column(name = "employeeName")
	private String employeeName;
	@Column(name = "password")
	private String password;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "is_active")
	private int isActive;
	@Column(name = "is_deleted")
	private int isDeleted;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "t_employee_roles", 
        joinColumns = @JoinColumn(
          name = "employee_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id")) 
    private Set<Role> roles;
	
}
