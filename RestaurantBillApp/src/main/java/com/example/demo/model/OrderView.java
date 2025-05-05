package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class OrderView {
	private int orderId;
    private int tableId;
    private int staffId;
    private String staffName;
    private Date ordDate;
    private BigDecimal orderTotal;
    private String orderStatus;
    private int menuId;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal itemTotal;
}
