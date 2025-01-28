package com.employee.onboarding.userAuthentication.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.employee.onboarding.userAuthentication.entity.Task;
import com.employee.onboarding.userAuthentication.entity.User;
import com.employee.onboarding.userAuthentication.enummeration.Priority;
import com.employee.onboarding.userAuthentication.enummeration.TaskStatus;
import com.employee.onboarding.userAuthentication.exception.TaskAlreadyExistsException;
import com.employee.onboarding.userAuthentication.pojoRequest.TaskRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.TaskResponse;
import com.employee.onboarding.userAuthentication.repository.TaskRepo;
import com.employee.onboarding.userAuthentication.repository.UserRepo;
import com.employee.onboarding.userAuthentication.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public void createTask(TaskRequest taskRequest) {
		log.info("Processing task creation for title: {}", taskRequest.getTitle());

		if (taskRepo.existsByTitle(taskRequest.getTitle())) {
			log.error("Task with title '{}' already exists.", taskRequest.getTitle());
			throw new TaskAlreadyExistsException("Task with the title already exists.");
		}

		Task task = new Task();
		task.setTitle(taskRequest.getTitle());
		task.setDescription(taskRequest.getDescription());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy : HH:mm");
		try {
			LocalDateTime dueDate = LocalDateTime.parse(taskRequest.getDueDate(), formatter);
			task.setDueDate(dueDate.format(formatter));
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid due date format. Expected format: dd/MM/yyyy : HH:mm");
		}
		task.setStatus(TaskStatus.PENDING);
		task.setPriority(taskRequest.getPriority());
		task.setCreatedAt(LocalDateTime.now());

		try {
			taskRepo.save(task);
			log.info("Task with title '{}' successfully saved.", taskRequest.getTitle());
		} catch (Exception e) {
			log.error("Error occurred while saving task: ", e);
			throw new RuntimeException("Failed to save the task. Please try again.");
		}
	}

	@Override
	public void updateTask(Long taskId, TaskRequest taskRequest) {
		Task existingTask = taskRepo.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found"));

		if (taskRequest.getTitle() != null) {
			existingTask.setTitle(taskRequest.getTitle());
		}
		if (taskRequest.getDescription() != null) {
			existingTask.setDescription(taskRequest.getDescription());
		}
		if (taskRequest.getPriority() != null) {
			existingTask.setPriority(taskRequest.getPriority());
		}
		if (taskRequest.getDueDate() != null) {
			existingTask.setDueDate(taskRequest.getDueDate());
		}
		existingTask.setUpdatedAt(LocalDateTime.now());

		taskRepo.save(existingTask);
	}

	@Override
	public List<TaskResponse> getAllTasks() {
		List<Task> tasks = taskRepo.findAll();

		return tasks.stream()
				.map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
						task.getPriority(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt()))
				.collect(Collectors.toList());
	}

	@Override
	public TaskResponse getTaskById(Long taskId) {
		return taskRepo.findById(taskId)
				.map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
						task.getPriority(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt()))
				.orElse(null);
	}

	@Override
	public List<TaskResponse> getTasksByStatus(TaskStatus status) {
		List<Task> tasks = taskRepo.findByStatus(status);

		return tasks.stream()
				.map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
						task.getPriority(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt()))
				.collect(Collectors.toList());
	}

	@Override
	public List<TaskResponse> getTasksByPriority(Priority priority) {
		List<Task> tasks = taskRepo.findByPriority(priority);

		return tasks.stream()
				.map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(),
						task.getPriority(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt()))
				.collect(Collectors.toList());
	}
	
	@Override
	public void deleteTask(Long taskId) {
        Task existingTask = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found"));

        taskRepo.delete(existingTask);
    }
	
	@Override
	public void assignTaskToUser(Long taskId, Long userId) throws IllegalAccessException {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found"));
        
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentRole = authentication.getAuthorities().toString();
        
        if (!currentRole.contains("ADMIN")) {
            throw new IllegalAccessException("Only users with the ADMIN role can assign tasks.");
        }

        if (task.getAssignedUser() != null && task.getAssignedUser().equals(user)) {
            throw new IllegalArgumentException("This task is already assigned to the specified user.");
        }

        task.setAssignedUser(user);

        taskRepo.save(task);
    }
}
