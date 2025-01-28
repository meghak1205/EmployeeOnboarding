package com.employee.onboarding.userAuthentication.pojoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
	
	private String name;
	private Long roleId;
	private String phoneNumber;
	private String description;
}
