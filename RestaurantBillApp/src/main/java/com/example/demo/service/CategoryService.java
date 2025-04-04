package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Category;
import com.example.demo.repository.*;


@Service("catService")
public class CategoryService {
	
	@Autowired
	private CategoryRepository catRepo;
	
	public boolean isAddNewCategory(Category category)
	{
		return catRepo.isAddNewCategory(category);
	}
	
	public List<Category> viewAllCategory()
	{
		return catRepo.viewAllCategory();
	}
}
