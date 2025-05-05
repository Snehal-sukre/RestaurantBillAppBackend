package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class DailyOrdersSummary {
	private LocalDate orderDate;
    private int totalOrders;
    private BigDecimal totalAmount;
}
