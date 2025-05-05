package com.example.demo.controller;

import com.example.demo.model.OrderMaster;
import com.example.demo.model.OrderView;
import com.example.demo.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:5173")
//@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderMaster order) {
        String result = orderService.placeOrder(order);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/viewAllOrders")
    public List<OrderView> viewAllOrders()
    {
    	List<OrderView> list=orderService.viewAllOrders();
    	if(list.size()!=0)
    	{
    		return list;
    	}
    	else
    	{
    		return null;
    	}
    }
    
    @GetMapping("/viewStaffOrders/{staffId}")
    public ResponseEntity<List<OrderView>> getOrdersByStaff(@PathVariable int staffId) {
        List<OrderView> list = orderService.viewOrdersByStaffId(staffId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }
    
    @PutMapping("/updateOrderStatus/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated successfully");
    }
    
    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
    
    @GetMapping("/searchOrderByStatus")
    public ResponseEntity<List<OrderView>> getOrdersWithItemsByStatus(@RequestParam String status) {
        List<OrderView> orders = orderService.getOrdersWithItemsByStatus(status);
        return ResponseEntity.ok(orders);
    }


}
