/*package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OrderItem;
import com.example.demo.service.OrderItemService;

@RestController
@CrossOrigin("http://localhost:5173")
public class OrderItemController {
	
	@Autowired
	OrderItemService ordItemsService;
	
	@PostMapping("/addOrderItems")
	public String addNewItem(@RequestBody OrderItem item)
	{
		boolean b=ordItemsService.isAddNewOrderItem(item);
		if(b)
		{
			return "Order Item Added Successfully in Order";
		}
		else
		{
			return "Some Problem In Adding Order Item to Order";
		}
	}

} */
