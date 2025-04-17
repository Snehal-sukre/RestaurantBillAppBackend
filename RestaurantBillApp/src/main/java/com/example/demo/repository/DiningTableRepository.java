package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DiningTable;
import com.example.demo.model.Menu;

@Repository("dineRepo")
public class DiningTableRepository {
	
	List<DiningTable> list;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isTableExists(int id)
	{
		int count=jdbcTemplate.queryForObject("select count(*) from dinning_table where table_id=?", new Object[] {id}, Integer.class);
		return count>0;
	}
	
	public boolean isAddNewTable(DiningTable table)
	{
		if(isTableExists(table.getId()))
		{
			return false;
		}
		int value=jdbcTemplate.update("insert into dinning_table values (?,?,?)", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, table.getId());
						ps.setInt(2, table.getCapacity());
						ps.setString(3, table.getStatus());
					}
				});
		return value>0;
	}
	
}
