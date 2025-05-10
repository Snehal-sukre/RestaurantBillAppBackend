package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.DailyOrdersSummary;
import com.example.demo.model.TopOrderedItems;
import com.example.demo.repository.ReportRepository;

@Service("reportService")
public class ReportService {
	
	@Autowired
	ReportRepository reportRepo;
	
	public List<DailyOrdersSummary> getDailyorders()
	{
		return reportRepo.getDailyOrders();
	}
	
	public List<TopOrderedItems> getTopOrderedItems()
	{
		return reportRepo.getTopOrderedItems();
	}
	
	public List<DailyOrdersSummary> getDailyOrdersByStaff(int staffId)
	{
		return reportRepo.getDailyOrdersByStaff(staffId);
	}
}
