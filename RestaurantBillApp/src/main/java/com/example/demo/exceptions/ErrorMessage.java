package com.example.demo.exceptions;

import lombok.Data;

@Data
public class ErrorMessage {
	private int statusCode;
	private String message;
	
	public ErrorMessage(int statusCode, String message)
	{
		this.message=message;
		this.statusCode=statusCode;
	}
}
