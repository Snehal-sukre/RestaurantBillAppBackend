package com.example.demo.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderMaster {
    private int orderId;
    private int tableId;
    private int staffId;
    private Date ordDate;
    private BigDecimal totalAmt;
    private String ordStatus;
    private List<OrderItem> orderItems; // List of food items in this order
}
