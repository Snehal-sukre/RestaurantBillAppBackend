/*package com.example.demo.controller;

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

import com.example.demo.model.OrderMaster;
import com.example.demo.service.OrderMasterService;

@RestController
@CrossOrigin("http://localhost:5173")
public class OrderMasterController {
	
	@Autowired
	OrderMasterService ordMasterService;
	
	@PostMapping("/addOrder")
	public String addNewOrder(@RequestBody OrderMaster order)
	{
		boolean b=ordMasterService.isAddNewOrder(order);
		if(b)
		{
			return "Order Master Added Successfully";
		}
		else
		{
			return "Order Master Not Added";
		}
	}
	
	@GetMapping("/viewOrders")
	public List<OrderMaster> viewAllOrders()
	{
		List<OrderMaster> list=ordMasterService.viewAllOrders();
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
	public OrderMaster searchOrderById(@PathVariable("orderid") Integer orderid)
	{
		OrderMaster order=ordMasterService.searchOrderById(orderid);
		if(order!=null)
		{
			return order;
		}
		else
		{
			return null;
		}
	}
	
	@DeleteMapping("/deleteOrderById/{orderid}")
	public String deleteOrderById(@PathVariable("orderid") Integer orderid)
	{
		boolean b=ordMasterService.isDeleteOrderById(orderid);
		if(b)
		{
			return "Order Deleted Successfully";
		}
		else
		{
			return "Some Problem in Deleting Order";
		}
	}
	
	@PutMapping("/updateOrder")
	public String updateOrder(@RequestBody OrderMaster order)
	{
		boolean b=ordMasterService.isUpdateOrder(order);
		if(b)
		{
			return "Order Updated Successfully";
		}
		else
		{
			return "Some Problem in Updating Order";
		}
	}
	
	@GetMapping("/searchOrderByPattern/{pattern}")
	public List<OrderMaster> searchOrderByPattern(@PathVariable("pattern") String pattern)
	{
		List<OrderMaster> list=ordMasterService.searchOrderByPattern(pattern);
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			return null;
		}
	}
} */
