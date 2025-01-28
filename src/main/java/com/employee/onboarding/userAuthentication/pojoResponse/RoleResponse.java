package com.employee.onboarding.userAuthentication.pojoResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
	
	private long id;
    private String name;
    private String description;
    private List<String> permissionNames;
}
