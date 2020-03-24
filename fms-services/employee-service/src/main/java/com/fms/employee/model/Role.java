/**
 * 
 */
package com.fms.employee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Kesavalu
 *
 */

@Data
@Entity
@Table(name="t_role")
@EqualsAndHashCode(of = {"roleId","roleName"})
@NoArgsConstructor
@RequiredArgsConstructor
public class Role extends Audit implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long roleId;
	
	@Column(name = "role_name")
	private @NonNull String roleName;
	
	@Column(name = "description")
	private @NonNull String description;
	
}
