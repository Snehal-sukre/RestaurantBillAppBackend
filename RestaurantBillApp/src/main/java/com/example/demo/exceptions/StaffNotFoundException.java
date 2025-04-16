package com.example.demo.exceptions;

public class StaffNotFoundException extends RuntimeException {
	private String message;
	public StaffNotFoundException(String message)
	{
		super(message);
		this.message=message;
	}
}
