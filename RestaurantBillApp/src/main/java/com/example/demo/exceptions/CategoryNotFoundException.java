package com.example.demo.exceptions;

public class CategoryNotFoundException extends RuntimeException {
	private String message;
	public CategoryNotFoundException(String message)
	{
		super(message);
		this.message=message;
	}

}
