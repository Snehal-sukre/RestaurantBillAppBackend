package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

@Service("ordService")
public class OrderService {
	
	@Autowired
	private OrderRepository ordRepo;
	
	public boolean isAddNewOrder(Order order)
	{
		return ordRepo.isAddNewOrder(order);
	}
	
	public List<Order> viewAllOrders()
	{
		return ordRepo.viewAllOrders();	
	}
	
	public Order searchOrderById(int id)
	{
		return ordRepo.searchOrderById(id);
	}
	
	public boolean isDeleteOrderById(int id)
	{
		return ordRepo.isDeleteOrderById(id);
	}
	
	public boolean isUpdateOrder(Order order)
	{
		return ordRepo.isUpdateOrder(order);
	}
	
	public List<Order> searchOrderByPattern(String pattern)
	{
		return ordRepo.searchOrderByPattern(pattern);
	}
}
