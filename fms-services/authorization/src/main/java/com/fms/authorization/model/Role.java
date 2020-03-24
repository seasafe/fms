/**
 * 
 */
package com.fms.authorization.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Kesavalu
 *
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {

	private @NonNull Long roleId;
	private @NonNull String roleName;
	private String description;
}
