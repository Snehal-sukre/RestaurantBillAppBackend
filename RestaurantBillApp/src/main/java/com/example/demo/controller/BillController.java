package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Bill;
import com.example.demo.model.OrderItem;
import com.example.demo.service.BillService;

@RestController
@CrossOrigin("http://localhost:5173")
public class BillController {
	
	@Autowired
	BillService billService;
	
	@PostMapping("/generateBill/{orderId}")
	public ResponseEntity<Bill> generateBill(@PathVariable int orderId) 
	{
        Bill bill = billService.generateBill(orderId);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllBills")
    public ResponseEntity<List<Bill>> getAllBills() 
    {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/viewOrderBill/{orderId}")
    public ResponseEntity<?> getFullBill(@PathVariable int orderId) {
        Bill bill = billService.getBillByOrderId(orderId);
        if (bill == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
        }

        // ✅ Attach item list to the bill
        List<OrderItem> items = billService.getOrderItems(orderId);
        bill.setItems(items);

        return ResponseEntity.ok(bill);
    }


}
