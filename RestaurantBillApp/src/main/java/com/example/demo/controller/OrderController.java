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
import com.example.demo.model.Order;
import com.example.demo.model.Staff;
import com.example.demo.service.OrderService;

@RestController
@CrossOrigin("http://localhost:5173")
public class OrderController {
	
	@Autowired
	OrderService ordService;
	
	@PostMapping("/addOrder")
	public String addOrder(@RequestBody Order order)
	{
		boolean b=ordService.isAddNewOrder(order);
		if(b)
		{
			return "Order Added Successfully";
		}
		else
		{
			return "Some Problem is There";
		}
	}
	
	@GetMapping("/viewOrders")
	public List<Order> viewAllOrders()
	{
		List<Order> list=ordService.viewAllOrders();
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
	
	@GetMapping("/searchOrderById/{orderid}")
	public Order searchOrderById(@PathVariable("orderid") Integer orderid)
	{
		Order order=ordService.searchOrderById(orderid);
		if(order!=null)
		{
			return order;
		}
		else
		{
			return null;
		}
	}
	
	@DeleteMapping("/deleteOrder/{orderid}")
	public String deleteOrderById(@PathVariable("orderid") Integer orderid)
	{
		boolean b=ordService.isDeleteOrderById(orderid);
		if(b)
		{
			return "Order Deleted Successfully";
		}
		else
		{
			return "Some Problem in Deleting Order with Id "+orderid;
		}
	}
	
	@PutMapping("/updateOrder")
	public String updateOrder(@RequestBody Order order)
	{
		boolean b=ordService.isUpdateOrder(order);
		if(b)
		{
			return "Order Record Updated Successfully";
		}
		else
		{
			return "Order Not Found With ID: "+order.getId();
		}
	}
	
	@GetMapping("/searchOrder/{pattern}")
	public List<Order> searchOrderByPattern(@PathVariable("pattern") String pattern)
	{
		List<Order> list=ordService.searchOrderByPattern(pattern);
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
}
