package com.employee.onboarding.userAuthentication.pojoRequest;

import com.employee.onboarding.userAuthentication.enummeration.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchAndListUserRequest {
	
	private String filterRole;
	private String filterUserName;
    private String filterPhoneNumber;
    private Status filterStatus;
}
