package com.employee.onboarding.userAuthentication.service;

import javax.management.relation.RoleNotFoundException;

import com.employee.onboarding.userAuthentication.entity.Role;
import com.employee.onboarding.userAuthentication.exception.RoleAlreadyExistsException;
import com.employee.onboarding.userAuthentication.pojoRequest.RoleRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.RoleResponse;

public interface RoleService {
	
	public Role createRole(RoleRequest roleRequest) throws RoleAlreadyExistsException;
	
	public RoleResponse getRole(String name) throws RoleNotFoundException;
	
	public void deleteRole(String name) throws RoleNotFoundException;
}
