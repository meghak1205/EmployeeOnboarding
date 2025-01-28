package com.employee.onboarding.userAuthentication.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.onboarding.userAuthentication.entity.Permission;
import com.employee.onboarding.userAuthentication.entity.Role;
import com.employee.onboarding.userAuthentication.exception.RoleAlreadyExistsException;
import com.employee.onboarding.userAuthentication.pojoRequest.RoleRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.RoleResponse;
import com.employee.onboarding.userAuthentication.repository.PermissionRepo;
import com.employee.onboarding.userAuthentication.repository.RoleRepo;
import com.employee.onboarding.userAuthentication.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PermissionRepo permissionRepo;
	
	@Override
	public Role createRole(RoleRequest roleRequest) throws RoleAlreadyExistsException {
	    if (roleRepo.existsByRoleName(roleRequest.getRoleName().toUpperCase())) {
	        throw new RoleAlreadyExistsException("Role with name '" + roleRequest.getRoleName() + "' already exists.");
	    }

	    List<Permission> permissions = permissionRepo.findAllById(roleRequest.getPermissionIds());
//	    List<Permission> permissions = permissionRepo.findByPermissionNameIn(List.of("READ", "WRITE"));

	    Role role = new Role();
	    role.setName(roleRequest.getRoleName().toUpperCase());
	    role.setDescription(roleRequest.getDescription());
	    role.setPermissions(permissions);

	    return roleRepo.save(role);
	}
	
	@Override
	public RoleResponse getRole(String name) throws RoleNotFoundException {
		Role role = roleRepo.findByName(name);
        if (role == null) {
            throw new RoleNotFoundException("Role not found with name: " + name);
        }

        RoleResponse response = new RoleResponse();
        response.setId(role.getRoleId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        
        List<String> permissionNames = Optional.ofNullable(role.getPermissions())
        	    .orElse(Collections.emptyList())
        	    .stream()
        	    .map(Permission::getPermissionName)
        	    .collect(Collectors.toList());
        response.setPermissionNames(permissionNames);

        return response;
    }
	
	@Override
	public void deleteRole(String name) throws RoleNotFoundException {
		Role role = roleRepo.findByName(name);
		if (role == null) {
            throw new RoleNotFoundException("Role not found.");
        }
        roleRepo.delete(role);
    }
}