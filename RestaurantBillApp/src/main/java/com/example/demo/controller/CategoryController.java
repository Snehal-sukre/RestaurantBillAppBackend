package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.service.*;
import com.example.demo.model.*;

@RestController
public class CategoryController {
	
	@Autowired
	CategoryService catService;
	
	@PostMapping("/addCategory")
	public String addCategory(@RequestBody Category category)
	{
		return "";
	}
}
