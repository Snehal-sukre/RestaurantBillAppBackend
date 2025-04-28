package com.example.demo.repository;

import com.example.demo.model.OrderItem;
import com.example.demo.model.OrderMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveOrderMaster(OrderMaster order) {
        String sql = "INSERT INTO order_master (table_id, staff_id, ord_date, total_amt, ord_status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getTableId(), order.getStaffId(), order.getOrdDate(), order.getTotalAmt(), order.getOrdStatus());
        
        Integer orderId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return orderId;
    }

    public void saveOrderItems(List<OrderItem> items, int orderId) {
        String sql = "INSERT INTO order_items (order_id, menu_id, quantity, total_amt) VALUES (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, items, items.size(), (ps, item) -> {
            ps.setInt(1, orderId);
            ps.setInt(2, item.getMenuId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getTotalAmt());
        });
    }
}
