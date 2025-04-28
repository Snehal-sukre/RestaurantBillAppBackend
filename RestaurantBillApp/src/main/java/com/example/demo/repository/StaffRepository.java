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

import com.example.demo.model.Staff;

@Repository("staffRepo")
public class StaffRepository {
	
	List<Staff> list;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isStaffExists(String email, String contact)
	{
		int count=jdbcTemplate.queryForObject("select count(*) from staff where email=? or contact_no=?", new Object[] {email, contact}, Integer.class);
		return count>0;
	}
	
	public boolean isAddNewStaff(Staff staff)
	{
		if(isStaffExists(staff.getEmail(), staff.getContact()))
		{
			return false;
		}
		int value=jdbcTemplate.update("insert into staff values('0', ?,?,?,?)", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, staff.getName());
						ps.setString(2, staff.getEmail());
						ps.setString(3, staff.getContact());
						ps.setBigDecimal(4, staff.getSalary());
					}
				});
		return value>0;
	}
	
	public List<Staff> getAllStaff()
	{
		list=jdbcTemplate.query("select *from staff order by staff_id", new RowMapper<Staff>()
				{
					@Override
					public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
						Staff staff=new Staff();
						staff.setId(rs.getInt(1));
						staff.setName(rs.getString(2));
						staff.setEmail(rs.getString(3));
						staff.setContact(rs.getString(4));
						staff.setSalary(rs.getBigDecimal(5));
						return staff;
					}
				});
		return list.size()>0?list:null;
	}
	
	public Staff searchStaffById(int staffid)
	{
		list=jdbcTemplate.query("select *from staff where staff_id=?", new Object[] {staffid}, new RowMapper<Staff>()
				{
					@Override
					public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
						Staff staff=new Staff();
						staff.setId(rs.getInt(1));
						staff.setName(rs.getString(2));
						staff.setEmail(rs.getString(3));
						staff.setContact(rs.getString(4));
						staff.setSalary(rs.getBigDecimal(5));
						return staff;
					}
				});
		return list.size()>0?list.get(0):null;
	}
	
	public boolean isDeleteStaffById(int id)
	{
		int value=jdbcTemplate.update("delete from staff where staff_id="+id);
		return value>0?true:false;
	}
	
	public boolean isUpdateStaff(Staff staff)
	{
		int value=jdbcTemplate.update("update staff set name=?, email=?, contact_no=?, salary=? where staff_id=?", new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, staff.getName());
						ps.setString(2, staff.getEmail());
						ps.setString(3, staff.getContact());
						ps.setBigDecimal(4, staff.getSalary());
						ps.setInt(5, staff.getId());
					}
				});
		return value>0?true:false;
	}
	
	public List<Staff> searchStaffByPattern(String pattern)
	{
		list=jdbcTemplate.query("select *from staff where name like '%"+pattern+"%'", new RowMapper<Staff>()
				{
					@Override
					public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
						Staff staff=new Staff();
						staff.setId(rs.getInt(1));
						staff.setName(rs.getString(2));
						staff.setEmail(rs.getString(3));
						staff.setContact(rs.getString(4));
						staff.setSalary(rs.getBigDecimal(5));
						return staff;
					}
				});	
		return list;
	}
}
