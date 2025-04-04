package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.*;

@Repository("catRepo")
public class CategoryRepository {
	
	List<Category> list;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isAddNewCategory(Category cat)
	{
		int value=jdbcTemplate.update("insert into category values('0', ?)", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, cat.getName());
					}
			
				});
		return value>0?true:false;
	}
	
	public List<Category> viewAllCategory()
	{
		list =jdbcTemplate.query("select *from category", new RowMapper<Category>()
				{
					@Override
					public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
						Category cat=new Category();
						cat.setId(rs.getInt(1));
						cat.setName(rs.getString(2));
						return cat;
					}
				});
		return list;
	}

}
