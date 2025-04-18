package com.example.demo.exceptions;

public class DiningTableNotFoundException extends RuntimeException {
	private String message;
	public DiningTableNotFoundException(String message)
	{
		super(message);
		this.message=message;
	}

}
