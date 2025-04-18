package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value=CategoryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorMessage handleCategoryException(CategoryNotFoundException exception)
	{
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	@ExceptionHandler(value=MenuNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorMessage handleMenuException(MenuNotFoundException exception)
	{
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	@ExceptionHandler(value=StaffNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorMessage handleStaffException(StaffNotFoundException exception)
	{
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	@ExceptionHandler(value=DiningTableNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorMessage handleDiningTableException(DiningTableNotFoundException exception)
	{
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
}
