package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class Bill {
	private int billId;
	private int orderId;
	private double totalAmount;
	private double discount;
	private double cgst;
	private double sgst;
	private double grandTotal;
	private Timestamp billDate;
	private String staffname;
	private int tableId;
	private List<OrderItem> items;
}
