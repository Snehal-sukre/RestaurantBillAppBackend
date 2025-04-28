package com.example.demo.request;

import java.util.List;
import com.example.demo.model.OrderItem;
import lombok.Data;

@Data
public class OrderRequest {
    private int tableId;
    private int staffId;
    private String ordStatus;
    private List<OrderItem> orderItems;
}
