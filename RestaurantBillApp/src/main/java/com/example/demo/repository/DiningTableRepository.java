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
						ps.setString(3, table.getAvailability());
					}
				});
		return value>0;
	}
	
	public List<DiningTable> viewAllTables()
	{
		list=jdbcTemplate.query("select *from dinning_table order by table_id", new RowMapper<DiningTable>()
				{
					@Override
					public DiningTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						DiningTable table=new DiningTable();
						table.setId(rs.getInt(1));
						table.setCapacity(rs.getInt(2));
						table.setAvailability(rs.getString(3));
						return table;
					}
				});
		return list;
	}
	
	public DiningTable searchTableById(int id)
	{
		list=jdbcTemplate.query("select *from dinning_table where table_id=?", new Object[] {id}, new RowMapper()
				{
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						DiningTable table=new DiningTable();
						table.setId(rs.getInt(1));
						table.setCapacity(rs.getInt(2));
						table.setAvailability(rs.getString(3));
						return table;
					}
				});
		return list.size()>0?list.get(0):null;
	}
	
	public boolean isDeleteTableById(int id)
	{
		int value=jdbcTemplate.update("delete from dinning_table where table_id="+id);
		return value>0?true:false;
	}
	
	public boolean isUpdateTable(DiningTable table)
	{
		int value=jdbcTemplate.update("update dinning_table set table_id=?, capacity=?,  availability_status=? where table_id=?", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, table.getId());
						ps.setInt(2, table.getCapacity());
						ps.setString(3, table.getAvailability());
						ps.setInt(4, table.getId());
					}
				});
		return value>0?true:false;
	}
	
	public List<DiningTable> searchTableByPattern(String pattern)
	{
		List<DiningTable> list=jdbcTemplate.query("select *from dinning_table where availability_status like '%"+pattern+"%'", new RowMapper<DiningTable>()
				{
					@Override
					public DiningTable mapRow(ResultSet rs, int rowNum) throws SQLException {
						DiningTable table=new DiningTable();
						table.setId(rs.getInt(1));
						table.setCapacity(rs.getInt(2));
						table.setAvailability(rs.getString(3));
						return table;
					}
				});
		return list;
	}
}
