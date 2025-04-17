package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.StaffNotFoundException;
import com.example.demo.model.Staff;
import com.example.demo.service.StaffService;

@RestController
@CrossOrigin("http://localhost:5173")

public class StaffController {
	
	@Autowired
	StaffService staffService;
	
	@PostMapping("/addstaff")
	public String addStaff(@RequestBody Staff staff)
	{
		boolean b=staffService.isAddNewStaff(staff);
		if(b)
		{
			return "Staff Added Successfully";
		}
		else
		{
			return "Staff Already Exists";
		}
	}
	
	@GetMapping("/viewstaff")
	public List<Staff> getAllStaff()
	{
		List<Staff> list=staffService.getAllStaff();
		//System.out.println(list);
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			throw new StaffNotFoundException("There is No Staff Data Present in Database");
		}
	}
	
	@GetMapping("/searchStaffById/{staffid}")
	public Staff searchStaffById(@PathVariable("staffid") Integer staffid)
	{
		Staff staff=staffService.searchStaffById(staffid);
		if(staff!=null)
		{
			return staff;
		}
		else
		{
			throw new StaffNotFoundException("Staff Not Found With Id: "+staffid);
		}
	}
	
	@DeleteMapping("/deletestaff/{staffid}")
	public String deleteStaffById(@PathVariable("staffid") Integer id)
	{
		boolean b=staffService.isDeleteStaffById(id);
		if(b)
		{
			return "Staff Deleted Successfully";
		}
		else
		{
			throw new StaffNotFoundException("Staff Not Found With Id: "+id);
		}
	}
	
	@PutMapping("/updateStaff")
	public String updateStaff(@RequestBody Staff staff)
	{
		boolean b=staffService.isUpdateStaff(staff);
		if(b)
		{
			return "Staff Record Updated Successfully";
		}
		else
		{
			throw new StaffNotFoundException("Staff Not Found With Id: "+staff.getId()); 
		}
	}
	
	@GetMapping("/searchStaff/{pattern}")
	public List<Staff> searchStaffByPattern(@PathVariable("pattern") String pattern)
	{
		List<Staff> list=staffService.searchStaffByPattern(pattern);
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			throw new StaffNotFoundException("Staff Not Found");
		}
	}
}
