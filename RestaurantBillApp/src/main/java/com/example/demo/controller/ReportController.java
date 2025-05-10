package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DailyOrdersSummary;
import com.example.demo.model.TopOrderedItems;
import com.example.demo.service.ReportService;

@RestController
@CrossOrigin("http://localhost:5173")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("/getDailyOrders")
	public List<DailyOrdersSummary> getDailyOrders()
	{
		return reportService.getDailyorders();
	}
	
	@GetMapping("/getTopOrderedItems")
	public List<TopOrderedItems> getTopOrderedItems()
	{
		return reportService.getTopOrderedItems();
	}
	
	@GetMapping("/getDailyOrdersByStaff/{staffId}")
	public List<DailyOrdersSummary> getDailyOrdersByStaff(@PathVariable int staffId) {
	    return reportService.getDailyOrdersByStaff(staffId);
	}

}
