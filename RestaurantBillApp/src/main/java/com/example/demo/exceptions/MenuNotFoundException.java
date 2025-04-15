package com.example.demo.exceptions;

public class MenuNotFoundException extends RuntimeException {
	private String message;
	public MenuNotFoundException(String message)
	{
		super(message);
		this.message=message;
	}

}
