package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.demo.model.*;

@Repository("catRepo")
public class CategoryRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isAddNewCategory(Category cat)
	{
		return false;
	}

}
