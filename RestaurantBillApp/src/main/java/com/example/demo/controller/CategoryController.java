package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.service.*;
import com.example.demo.model.*;

@RestController
@CrossOrigin("http://localhost:5173")
public class CategoryController {
	
	@Autowired
	CategoryService catService;
	
	@PostMapping("/addCategory")
	public String addCategory(@RequestBody Category category)
	{
		boolean b=catService.isAddNewCategory(category);
		if(b)
		{
			return "Category Added Successfully";
		}
		else
		{
			return "Category Not Added";
		}
	}
	
	@GetMapping("/viewCategory")
	public List<Category> viewAllCategory()
	{
		List<Category> list=catService.viewAllCategory();
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
}
