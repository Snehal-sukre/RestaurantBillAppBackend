package com.example.demo.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private int itemId;
    private int orderId;
    private int menuId;
    private String itemName;
    private int quantity;
    private BigDecimal totalAmt;
}
