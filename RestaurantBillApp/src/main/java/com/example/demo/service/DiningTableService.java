package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.DiningTable;
import com.example.demo.repository.DiningTableRepository;

@Service("dineService")
public class DiningTableService {
	
	@Autowired
	DiningTableRepository dineRepo;
	
	public boolean isAddNewTable(DiningTable table)
	{
		return dineRepo.isAddNewTable(table);
	}
	
	public List<DiningTable> viewAllTables()
	{
		return dineRepo.viewAllTables();
	}
	
	public DiningTable searchTableById(int id)
	{
		return dineRepo.searchTableById(id);
	}
	
	public boolean isDeleteTableById(int id)
	{
		return dineRepo.isDeleteTableById(id);
	}
	
	public boolean isUpdateTable(DiningTable table)
	{
		return dineRepo.isUpdateTable(table);
	}
	
	public List<DiningTable> searchTableByPattern(String pattern)
	{
		return dineRepo.searchTableByPattern(pattern);
	}
}
