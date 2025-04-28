package com.example.demo.service;

import com.example.demo.model.OrderMaster;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public String placeOrder(OrderMaster order) {
        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : order.getOrderItems()) {
            totalAmount = totalAmount.add(item.getTotalAmt());
        }
        order.setTotalAmt(totalAmount);
        order.setOrdStatus("Pending");

        // Save order_master
        int orderId = orderRepository.saveOrderMaster(order);

        // Save order_items
        for (OrderItem item : order.getOrderItems()) {
            item.setOrderId(orderId); // Set orderId for each item
        }
        orderRepository.saveOrderItems(order.getOrderItems(), orderId);

        return "Order placed successfully with ID: " + orderId;
    }
}
