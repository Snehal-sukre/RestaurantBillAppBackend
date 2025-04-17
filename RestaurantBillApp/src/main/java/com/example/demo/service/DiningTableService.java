package com.example.demo.service;

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
}
