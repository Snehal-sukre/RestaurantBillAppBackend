package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.service.*;
import com.example.demo.exceptions.CategoryNotFoundException;
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
			return "Category Already Exists";
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
			throw new CategoryNotFoundException("There is Category Not Present in Database");
		}
	}
	
	@GetMapping("/searchCatById/{catid}")
	public Category searchCategoryById(@PathVariable("catid") Integer id)
	{
		Category cat=catService.searchCategoryById(id);
		if(cat!=null)
		{
			return cat;
		}
		else
		{
			throw new CategoryNotFoundException("Category Not Found Using ID: "+id);
		}
	}
	
	@DeleteMapping("/deleteById/{catid}")
	public String deleteCategoryById(@PathVariable("catid") Integer catid)
	{
		boolean b=catService.isDeleteCategoryById(catid);
		if(b)
		{
			return "Category Deleted Successfully";
		}
		else
		{
			throw new CategoryNotFoundException("Category Not Found Using ID: "+catid);
		}
	}
	
	@PutMapping("/updateCategory")
	public String updateCategory(@RequestBody Category category)
	{
		boolean b=catService.isUpdateCategory(category);
		if(b)
		{
			return "Category Record Updated Successfully";
		}
		else
		{
			throw new CategoryNotFoundException("Category Not Found Using ID: "+category.getId());
		}
	}
	
	@GetMapping("/search/{pattern}")
	public List<Category> searchCategoryByPattern(@PathVariable("pattern") String pattern)
	{
		List<Category> list=catService.searchCategoryByPattern(pattern);
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			throw new CategoryNotFoundException("Category Not Found in Database");
		}
	}
}
