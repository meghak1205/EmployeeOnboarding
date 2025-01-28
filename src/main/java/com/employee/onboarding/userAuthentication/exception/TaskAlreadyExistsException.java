package com.employee.onboarding.userAuthentication.exception;

public class TaskAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TaskAlreadyExistsException(String message)
	{
		super(message);
	}
}
