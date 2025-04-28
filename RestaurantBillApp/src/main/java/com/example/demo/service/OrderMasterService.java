/*package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrderMaster;
import com.example.demo.repository.OrderMasterRepository;

@Service("ordMasterService")
public class OrderMasterService {
	
	@Autowired
	OrderMasterRepository ordMasterRepo;
	
	public boolean isAddNewOrder(OrderMaster order)
	{
		return ordMasterRepo.isAddNewOrder(order);
	}
	
	public List<OrderMaster> viewAllOrders()
	{
		return ordMasterRepo.viewAllOrders();
	}
	
	public OrderMaster searchOrderById(int id)
	{
		return ordMasterRepo.searchOrderById(id);
	}
	
	public boolean isDeleteOrderById(int id)
	{
		return ordMasterRepo.isDeleteOrderById(id);
	}
	
	public boolean isUpdateOrder(OrderMaster order)
	{
		return ordMasterRepo.isUpdateOrder(order);
	}
	
	public List<OrderMaster> searchOrderByPattern(String pattern)
	{
		return ordMasterRepo.searchOrderByPattern(pattern);
	}
} */
