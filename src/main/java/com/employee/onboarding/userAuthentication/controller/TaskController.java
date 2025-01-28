package com.employee.onboarding.userAuthentication.controller;




import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.onboarding.userAuthentication.enummeration.Priority;
import com.employee.onboarding.userAuthentication.enummeration.TaskStatus;
import com.employee.onboarding.userAuthentication.pojoRequest.TaskRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.Message;
import com.employee.onboarding.userAuthentication.pojoResponse.TaskResponse;
import com.employee.onboarding.userAuthentication.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	private Logger log = LoggerFactory.getLogger(getClass());

	private static final String CREATE_TASK = "/create";
	private static final String UPDATE_TASK = "/update";
	private static final String GET_ALL_TASK = "/getAll";
	private static final String GET_TASK_BY_TASK_ID = "/{taskId}";
	private static final String GET_TASK_BY_STATUS = "/get/status";
	private static final String GET_TASK_BY_PRIORITY = "/get/priority";
	private static final String DELETE_TASK_BY_TASK_ID = "/delete/{taskId}";
	private static final String ASSIGN_TASK_TO_USER = "/assign/{taskId}/{userId}";

	@Operation(summary = "Create task for user")
	@PostMapping(value = CREATE_TASK)
	public ResponseEntity<Message> createTask(@Valid @ParameterObject TaskRequest taskRequest) {
		log.info("Received request to create a new task with title: {}", taskRequest.getTitle());
		try {
			taskService.createTask(taskRequest);
			log.info("Task created successfully: {}", taskRequest.getTitle());
			return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Task created successfully"));
		} catch (IllegalArgumentException e) {
			log.error("Task creation failed. Validation error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
		} catch (Exception e) {
			log.error("Unexpected error during task creation: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Message("Task creation failed. Please try again later."));
		}
	}

	@Operation(summary = "Update an existing task")
	@PutMapping(value = UPDATE_TASK)
	public ResponseEntity<Message> updateTask(@RequestParam Long taskId,
			@Valid @ParameterObject TaskRequest taskRequest) {
		log.info("Received request to update task with ID: {}", taskId);
		try {
			taskService.updateTask(taskId, taskRequest);
			log.info("Task updated successfully: {}", taskId);
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Task updated successfully"));
		} catch (IllegalArgumentException e) {
			log.error("Task update failed. Validation error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
		} catch (Exception e) {
			log.error("Unexpected error during task update: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Message("Task update failed. Please try again later."));
		}
	}

	@Operation(summary = "Fetch all tasks")
	@GetMapping(value = GET_ALL_TASK)
	public ResponseEntity<List<TaskResponse>> fetchAllTasks() {
		log.info("Received request to fetch all tasks");
		try {
			List<TaskResponse> tasks = taskService.getAllTasks();
			return ResponseEntity.status(HttpStatus.OK).body(tasks);
		} catch (Exception e) {
			log.error("Error occurred while fetching tasks: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}

	@Operation(summary = "Fetch task by ID")
	@GetMapping(value = GET_TASK_BY_TASK_ID)
	public ResponseEntity<TaskResponse> fetchTaskById(@PathVariable Long taskId) {
		log.info("Received request to fetch task with ID: {}", taskId);
		try {
			TaskResponse task = taskService.getTaskById(taskId);
			if (task != null) {
				return ResponseEntity.status(HttpStatus.OK).body(task);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			log.error("Error occurred while fetching task with ID: {}", taskId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@Operation(summary = "Fetch tasks by status")
	@GetMapping(value = GET_TASK_BY_STATUS)
	public ResponseEntity<List<TaskResponse>> fetchTasksByStatus(@RequestParam TaskStatus status) {
		log.info("Received request to fetch tasks by status: {}", status);
		try {
			List<TaskResponse> tasks = taskService.getTasksByStatus(status);
			return ResponseEntity.status(HttpStatus.OK).body(tasks);
		} catch (Exception e) {
			log.error("Error occurred while fetching tasks by status: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}

	@Operation(summary = "Fetch tasks by priority")
	@GetMapping(value = GET_TASK_BY_PRIORITY)
	public ResponseEntity<List<TaskResponse>> fetchTasksByPriority(@RequestParam Priority priority) {
		log.info("Received request to fetch tasks by priority: {}", priority);
		try {
			List<TaskResponse> tasks = taskService.getTasksByPriority(priority);
			return ResponseEntity.status(HttpStatus.OK).body(tasks);
		} catch (Exception e) {
			log.error("Error occurred while fetching tasks by priority: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}

	@Operation(summary = "Delete task by ID")
	@DeleteMapping(value = DELETE_TASK_BY_TASK_ID)
	public ResponseEntity<Message> deleteTaskById(@PathVariable Long taskId) {
		log.info("Received request to delete task with ID: {}", taskId);
		try {
			taskService.deleteTask(taskId);
			log.info("Task deleted successfully: {}", taskId);
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Task deleted successfully"));
		} catch (Exception e) {
			log.error("Error occurred while deleting task with ID: {}", taskId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Message("Task deletion failed. Please try again later."));
		}
	}

	@Operation(summary = "Assign task to user")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = ASSIGN_TASK_TO_USER)
	public ResponseEntity<Message> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId)
			throws Exception {
		log.info("Received request to assign task with ID: {} to user with ID: {}", taskId, userId);
		try {
			taskService.assignTaskToUser(taskId, userId);
			log.info("Task with ID: {} successfully assigned to user with ID: {}", taskId, userId);
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Task successfully assigned to user"));
		} catch (IllegalAccessException e) {
			log.error("Unauthorized access: ", e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message(e.getMessage()));
		} catch (IllegalArgumentException e) {
			log.error("Error occurred while assigning task: ", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
		} catch (Exception e) {
			log.error("Error occurred while assigning task with ID: {} to user with ID: {}", taskId, userId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Message("Task assignment failed. Please try again later."));
		}
	}

}

