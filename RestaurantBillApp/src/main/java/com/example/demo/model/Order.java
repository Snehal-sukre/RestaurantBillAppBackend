package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class Order {
	private int id;
	private int tableId;
	private int staffId;
	private String staffName;
	private Date ordDate;
	private int menuId;
	private String menuName;
	private int quantity;
	private BigDecimal totalAmt;
}
