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

import com.example.demo.exceptions.DiningTableNotFoundException;
import com.example.demo.exceptions.MenuNotFoundException;
import com.example.demo.model.DiningTable;
import com.example.demo.model.Menu;
import com.example.demo.service.DiningTableService;

@RestController
@CrossOrigin("http://localhost:5173")
public class DiningTableController {

	@Autowired
	DiningTableService dineService;
	
	@PostMapping("/addtable")
	public String addNewTable(@RequestBody DiningTable table)
	{
		boolean b=dineService.isAddNewTable(table);
		if(b)
		{
			return "Dining Table Added Successfully";
		}
		else
		{
			return "Dining Table Already Exists";
		}
	}
	
	@GetMapping("/viewtables")
	public List<DiningTable> viewAllTables()
	{
		List<DiningTable> list=dineService.viewAllTables();
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			throw new DiningTableNotFoundException("Dining Table Not Present in Database");
		}
	}
	
	@GetMapping("/searchTableById/{tableid}")
	public DiningTable searchTableById(@PathVariable("tableid") Integer tableid)
	{
		DiningTable table=dineService.searchTableById(tableid);
		if(table!=null)
		{
			return table;
		}
		else
		{
			throw new DiningTableNotFoundException("Dining Table Not Found With Id: "+tableid);
		}
	}
	
	@DeleteMapping("/deleteTableById/{tableid}")
	public String deleteTableById(@PathVariable("tableid") Integer tableid)
	{
		boolean b=dineService.isDeleteTableById(tableid);
		if(b)
		{
			return "Dining Table Deleted Successfully";
		}
		else
		{
			throw new DiningTableNotFoundException("Dining Table Not Found With Id: "+tableid);
		}
	}
	
	@PutMapping("/updateTable")
	public String updateTable(@RequestBody DiningTable table)
	{
		boolean b=dineService.isUpdateTable(table);
		if(b)
		{
			return "Dining Table Updated Successfully";
		}
		else
		{
			throw new DiningTableNotFoundException("Dining Table Not Found With Id: "+table.getId());
		}
	}
	
	@GetMapping("/searchTable/{pattern}")
	public List<DiningTable> searchTableByPattern(@PathVariable("pattern") String pattern)
	{
		List<DiningTable> list=dineService.searchTableByPattern(pattern);
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			throw new DiningTableNotFoundException("Dining Table Not Found");
		}
	}
}
