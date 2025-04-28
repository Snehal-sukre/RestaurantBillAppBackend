package com.example.demo.repository;

import com.example.demo.model.OrderItem;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int[] save(List<OrderItem> items) {
        String query = "INSERT INTO order_items (order_id, menu_id, quantity, total_amt) VALUES (?, ?, ?, ?)";
        
        return jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OrderItem item = items.get(i);
                ps.setInt(1, item.getOrderId());
                ps.setInt(2, item.getMenuId());
                ps.setInt(3, item.getQuantity());
                ps.setBigDecimal(4, item.getTotalAmt());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
