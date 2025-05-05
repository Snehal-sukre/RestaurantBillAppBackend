package com.example.demo.repository;

import com.example.demo.model.OrderItem;
import com.example.demo.model.OrderMaster;
import com.example.demo.model.OrderView;

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
    
    public List<OrderView> viewAllOrders() {
        String sql = "SELECT om.order_id, om.table_id, s.name AS staff_name, om.ord_date, om.ord_status, " +
                     "m.item_name, m.price, oi.quantity " +
                     "FROM order_master om " +
                     "JOIN staff s ON om.staff_id = s.staff_id " +
                     "JOIN order_items oi ON om.order_id = oi.order_id " +
                     "JOIN menu m ON oi.menu_id = m.id " +
                     "ORDER BY om.order_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            OrderView order = new OrderView();
            order.setOrderId(rs.getInt("order_id"));
            order.setTableId(rs.getInt("table_id"));
            order.setStaffName(rs.getString("staff_name")); // use staffName
            order.setOrdDate(rs.getDate("ord_date"));
            order.setOrderStatus(rs.getString("ord_status"));
            order.setItemName(rs.getString("item_name"));   // item name
            order.setPrice(rs.getBigDecimal("price"));      // rate or price
            order.setQuantity(rs.getInt("quantity"));       // quantity
            return order;
        });
    }
    
    public List<OrderView> viewOrdersByStaffId(int staffId) {
        String sql = "SELECT om.order_id, om.table_id, om.staff_id, s.name AS staff_name, om.ord_date, " +
                     "om.ord_status, m.id AS menu_id, m.item_name, m.price, oi.quantity, " +
                     "(m.price * oi.quantity) AS item_total " +
                     "FROM order_master om " +
                     "JOIN staff s ON om.staff_id = s.staff_id " +
                     "JOIN order_items oi ON om.order_id = oi.order_id " +
                     "JOIN menu m ON oi.menu_id = m.id " +
                     "WHERE om.staff_id = ? " +
                     "ORDER BY om.order_id";

        return jdbcTemplate.query(sql, new Object[]{staffId}, (rs, rowNum) -> {
            OrderView order = new OrderView();
            order.setOrderId(rs.getInt("order_id"));
            order.setTableId(rs.getInt("table_id"));
            order.setStaffId(rs.getInt("staff_id"));
            order.setStaffName(rs.getString("staff_name"));
            order.setOrdDate(rs.getDate("ord_date"));
            order.setOrderStatus(rs.getString("ord_status"));
            order.setMenuId(rs.getInt("menu_id"));
            order.setItemName(rs.getString("item_name"));
            order.setPrice(rs.getBigDecimal("price"));
            order.setQuantity(rs.getInt("quantity"));
            order.setItemTotal(rs.getBigDecimal("item_total"));
            return order;
        });
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE order_master SET ord_status = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, newStatus, orderId);
    }
    
    public void deleteOrder(int orderId) {
        // First delete order items (because they depend on order ID)
        String deleteOrderItemsSql = "DELETE FROM order_items WHERE order_id = ?";
        jdbcTemplate.update(deleteOrderItemsSql, orderId);

        // Then delete the order master
        String deleteOrderMasterSql = "DELETE FROM order_master WHERE order_id = ?";
        jdbcTemplate.update(deleteOrderMasterSql, orderId);
    }
    
    public List<OrderView> findOrdersWithItemsByStatus(String status) {
        String sql = "SELECT om.order_id, om.table_id, om.staff_id, om.ord_date, om.total_amt, om.ord_status, " +
                     "oi.menu_id, oi.quantity, oi.total_amt AS item_total " +
                     "FROM order_master om " +
                     "JOIN order_items oi ON om.order_id = oi.order_id " +
                     "WHERE om.ord_status = ?";

        return jdbcTemplate.query(sql, new Object[]{status}, (rs, rowNum) -> {
            OrderView view = new OrderView();
            view.setOrderId(rs.getInt("order_id"));
            view.setTableId(rs.getInt("table_id"));
            view.setStaffId(rs.getInt("staff_id"));
            view.setOrdDate(rs.getDate("ord_date"));
            view.setOrderTotal(rs.getBigDecimal("total_amt"));
            view.setOrderStatus(rs.getString("ord_status"));
            view.setMenuId(rs.getInt("menu_id"));
            view.setQuantity(rs.getInt("quantity"));
            view.setItemTotal(rs.getBigDecimal("item_total"));
            return view;
        });
    }
}

