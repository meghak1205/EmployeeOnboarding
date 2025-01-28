package com.employee.onboarding.userAuthentication.pojoRequest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
	private String roleName;
	private String description;
	private List<Long> permissionIds;
}
