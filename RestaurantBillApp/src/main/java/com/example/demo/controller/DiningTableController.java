package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DiningTable;
import com.example.demo.service.DiningTableService;

@RestController
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
}
