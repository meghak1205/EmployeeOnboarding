package com.employee.onboarding.userAuthentication.controller;

import javax.management.relation.RoleNotFoundException;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.onboarding.userAuthentication.entity.Role;
import com.employee.onboarding.userAuthentication.exception.RoleAlreadyExistsException;
import com.employee.onboarding.userAuthentication.pojoRequest.RoleRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.Message;
import com.employee.onboarding.userAuthentication.pojoResponse.RoleResponse;
import com.employee.onboarding.userAuthentication.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RolePermissionController {
	
	@Autowired
    private RoleService roleService;
	
	private static final String CREATE_ROLE = "/create";
	private static final String GET_BY_NAME = "/get/{name}";
	private static final String DELETE_BY_NAME = "/delete/{name}";

	@PostMapping(value = CREATE_ROLE)
	@Operation(summary = "Create a new role", description = "Create a new role in the system.")
    public ResponseEntity<Message> createRole(@ParameterObject @Valid RoleRequest roleRequest) {
        try {
            Role newRole = roleService.createRole(roleRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Message("Role '" + newRole.getName() + "' created successfully."));
        } catch (RoleAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message("Role with name '" + roleRequest.getRoleName() + "' already exists."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message("Failed to create role. Please try again later."));
        }
    }
	
	@GetMapping(value = GET_BY_NAME)
	@Operation(summary = "Get a role by name", description = "Fetch role details using the role name.")
    public ResponseEntity<?> getRole(@PathVariable String name) {
        try {
        	RoleResponse role = roleService.getRole(name);
            return ResponseEntity.ok(role);
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("Role not found with name or ID: " + name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message("Failed to retrieve role. Please try again later."));
        }
    }
	
	@DeleteMapping(value = DELETE_BY_NAME)
	@Operation(summary = "Delete a role by name", description = "Delete a role from the system.")
    public ResponseEntity<Message> deleteRole(@PathVariable String name) {
        try {
            roleService.deleteRole(name);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Message("Role '" + name + "' deleted successfully."));
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Message("Role not found with name or ID: " + name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Message("Failed to delete role. Please try again later."));
        }
    }
}
