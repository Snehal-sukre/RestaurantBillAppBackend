package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;

@Service("staffService")
public class StaffService {
	
	@Autowired
	StaffRepository staffRepo;
	
	public boolean isAddNewStaff(Staff staff)
	{
		return staffRepo.isAddNewStaff(staff);
	}
	
	public List<Staff> getAllStaff()
	{
		return staffRepo.getAllStaff();
	}
	
	public Staff searchStaffById(int staffid)
	{
		return staffRepo.searchStaffById(staffid);
	}
	
	public boolean isDeleteStaffById(int id)
	{
		return staffRepo.isDeleteStaffById(id);
	}
	
	public boolean isUpdateStaff(Staff staff)
	{
		return staffRepo.isUpdateStaff(staff);
	}
	
	public List<Staff> searchStaffByPattern(String pattern)
	{
		return staffRepo.searchStaffByPattern(pattern);
	}
}
