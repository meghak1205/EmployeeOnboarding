package com.employee.onboarding.userAuthentication.pojoResponse;

import java.time.LocalDateTime;

import com.employee.onboarding.userAuthentication.enummeration.Priority;
import com.employee.onboarding.userAuthentication.enummeration.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
	
	private Long id;
    private String title;
    private String description;
    private String dueDate;
    private Priority priority;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
