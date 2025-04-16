package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Menu;

@Repository("menuRepo")
public class MenuRepository {
	
	List<Menu> list;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isMenuExists(String name)
	{
		int count=jdbcTemplate.queryForObject("select count(*) from menu where item_name=?", new Object[] {name}, Integer.class);
		return count>0;
	}
	
	public boolean isAddNewMenu(Menu menu)
	{
		if(isMenuExists(menu.getName()))
		{
			return false;
		}
		int value=jdbcTemplate.update("insert into menu values ('0', ?,?,?,?)", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, menu.getName());
						ps.setInt(2, menu.getCategoryId());
						ps.setBigDecimal(3, menu.getPrice());
						ps.setString(4, menu.getDescription());
					}
				});
		return value>0;
	}
	
	public List<Menu> viewAllMenus()
	{
		list=jdbcTemplate.query("select m.id, m.item_name,m.category_id, c.name as category_name, m.price, m.description from menu m inner join category c on m.category_id=c.id order by m.id", new RowMapper<Menu>()
				{
					@Override
					public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
						Menu menu=new Menu();
						menu.setId(rs.getInt(1));
						menu.setName(rs.getString(2));
						menu.setCategoryId(rs.getInt(3));
						menu.setCategoryName(rs.getString(4));
						menu.setPrice(rs.getBigDecimal(5));
						menu.setDescription(rs.getString(6));
						return menu;
					}
			
				});
		return list;
	}
	
	public Menu searchMenuById(int id)
	{
		list=jdbcTemplate.query("select *from menu where id=?", new Object[] {id}, new RowMapper()
				{
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Menu menu=new Menu();
						menu.setId(rs.getInt(1));
						menu.setName(rs.getString(2));
						menu.setCategoryId(rs.getInt(3));
						menu.setPrice(rs.getBigDecimal(4));
						menu.setDescription(rs.getString(5));
						return menu;
					}
				});
		return list.size()>0?list.get(0):null;
	}
	
	public boolean isDeleteMenuById(int id)
	{
		int value=jdbcTemplate.update("delete from menu where id="+id);
		return value>0?true:false;
	}
	
	public boolean isUpdateMenu(Menu menu)
	{
		int value=jdbcTemplate.update("update menu set item_name=?, category_id=?, price=?, description=? where id=?", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, menu.getName());
						ps.setInt(2, menu.getCategoryId());
						ps.setBigDecimal(3, menu.getPrice());
						ps.setString(4, menu.getDescription());
						ps.setInt(5, menu.getId());
					}
				});
		return value>0?true:false;
	}
	
	public List<Menu> searchMenuByPattern(String pattern)
	{
		List<Menu> list=jdbcTemplate.query("select m.id, m.item_name, m.category_id, c.name as category_name, m.price, m.description from menu m inner join category c on m.category_id=c.id where m.item_name like '%"+pattern+"%' order by m.id", new RowMapper<Menu>()
				{
					@Override
					public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
						Menu menu=new Menu();
						menu.setId(rs.getInt(1));
						menu.setName(rs.getString(2));
						menu.setCategoryId(rs.getInt(3));
						menu.setCategoryName(rs.getString(4));
						menu.setPrice(rs.getBigDecimal(5));
						menu.setDescription(rs.getString(6));
						return menu;
					}
				});
		return list;
	}
}
