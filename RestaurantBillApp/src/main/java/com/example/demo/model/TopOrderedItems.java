package com.example.demo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TopOrderedItems {
	private String itemName;
	private int totalQty;
	private BigDecimal totalAmt;
}
