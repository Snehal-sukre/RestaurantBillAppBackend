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
	
	public boolean isCategoryExists(String name)
	{
		//String s= "select count(*) from category where name =?";
		int count=jdbcTemplate.queryForObject("select count(*) from category where name=?", new Object[] {name}, Integer.class);
		return count>0;
	}
	
	public boolean isAddNewCategory(Category cat)
	{
		if(isCategoryExists(cat.getName()))
		{
			return false;
		}
		int value=jdbcTemplate.update("insert into category values('0', ?)", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, cat.getName());
					}
			
				});
		return value>0;
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
	
	public Category searchCategoryById(int id)
	{
		list=jdbcTemplate.query("select *from employee where id=?",new Object[] {id}, new RowMapper()
				{
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Category cat=new Category();
						cat.setId(rs.getInt(1));
						cat.setName(rs.getString(2));
						return cat;
					}
				});
		return list.size()>0?list.get(0):null;
	}
	
	public boolean isDeleteCategoryById(int id)
	{
		int value=jdbcTemplate.update("delete from category where id="+id);
		return value>0?true:false;
	}
	
	public boolean isUpdateCategory(Category category)
	{
		int value=jdbcTemplate.update("update category set name=? where id=?", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, category.getName());
						ps.setInt(2, category.getId());
					}
				});
		return value>0?true:false;
	}
	
	public List<Category> searchCategoryByPattern(String pattern)
	{
		List<Category>list=jdbcTemplate.query("select *from category where name like '%"+pattern+"%'", new RowMapper<Category>()
				{
					@Override
					public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
						Category cat=new Category();
						cat.setId(rs.getInt(1));
						cat.setName(rs.getString(2));
						return cat;
					}
				});
		return list.size()>0?list:null;
	}
 
}
