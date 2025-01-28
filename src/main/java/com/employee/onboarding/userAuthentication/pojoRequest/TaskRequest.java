package com.employee.onboarding.userAuthentication.pojoRequest;

import com.employee.onboarding.userAuthentication.enummeration.Priority;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TaskRequest {
	private String title;
	private String description;
	
	@Schema(defaultValue = "30/01/2025 : 10:00")
	private String dueDate;
	
    private Priority priority;
}
