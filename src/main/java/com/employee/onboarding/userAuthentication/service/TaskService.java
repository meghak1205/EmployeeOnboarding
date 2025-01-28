package com.employee.onboarding.userAuthentication.service;

import java.util.List;

import com.employee.onboarding.userAuthentication.enummeration.Priority;
import com.employee.onboarding.userAuthentication.enummeration.TaskStatus;
import com.employee.onboarding.userAuthentication.pojoRequest.TaskRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.TaskResponse;

public interface TaskService {

	public void createTask(TaskRequest taskRequest);
	
	public void updateTask(Long taskId, TaskRequest taskRequest);
	
	public List<TaskResponse> getAllTasks();
	
	public TaskResponse getTaskById(Long taskId);
	
	public List<TaskResponse> getTasksByStatus(TaskStatus status);
	
	public List<TaskResponse> getTasksByPriority(Priority priority);
	
	public void deleteTask(Long taskId);
	
	public void assignTaskToUser(Long taskId, Long userId) throws IllegalAccessException;

}
