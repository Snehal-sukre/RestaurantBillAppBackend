package com.example.demo.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Bill;
import com.example.demo.model.OrderItem;

@Repository("billRepo")
public class BillRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Get the list of order items based on the order ID
    public List<OrderItem> getOrderItems(int orderId) {
        String sql = "SELECT oi.item_id, oi.order_id, oi.menu_id, oi.quantity, oi.total_amt, m.item_name " +
                     "FROM order_items oi " +
                     "JOIN menu m ON oi.menu_id = m.id " +
                     "WHERE oi.order_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            OrderItem item = new OrderItem();
            item.setItemId(rs.getInt("item_id"));
            item.setOrderId(rs.getInt("order_id"));
            item.setMenuId(rs.getInt("menu_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setTotalAmt(rs.getBigDecimal("total_amt"));
            item.setItemName(rs.getString("item_name"));
            return item;
        }, orderId);
    }

    // Get the name of the waiter who took the order
    public String getWaiterName(int orderId) {
        String sql = "SELECT s.name FROM order_master om " +
                     "JOIN staff s ON om.staff_id = s.staff_id WHERE om.order_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, orderId);
    }

    // Get the table number where the order was placed
    public int getTableNumber(int orderId) {
        String sql = "SELECT dt.table_id FROM order_master om " +
                     "JOIN dinning_table dt ON om.table_id = dt.table_id WHERE om.order_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, orderId);
    }

    // Get the total amount for an order
    public double getTotalAmount(int orderId) {
        String sql = "SELECT total_amt FROM order_master WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, orderId);
    }

    // Generate a bill for the order only if it doesn't already exist
    @Transactional
    public int generateBill(Bill bill) {
        // Check if a bill already exists for the given order ID
        Bill existingBill = getBillByOrderId(bill.getOrderId());
        if (existingBill != null) {
            // If a bill already exists, return the existing bill ID
            return existingBill.getBillId();
        }

        // If no bill exists, proceed to insert the new bill
        String sql = "INSERT INTO bill (order_id, total_amount, discount, cgst, sgst, grand_total, bill_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        jdbcTemplate.update(sql,
            bill.getOrderId(),
            bill.getTotalAmount(),
            bill.getDiscount(),
            bill.getCgst(),
            bill.getSgst(),
            bill.getGrandTotal()
        );

        // Return the generated bill ID
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    // Get all the bills
    public List<Bill> getAllBills() {
        String sql = "SELECT * FROM bill";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Bill bill = new Bill();
            bill.setBillId(rs.getInt("bill_id"));
            bill.setOrderId(rs.getInt("order_id"));
            bill.setTotalAmount(rs.getDouble("total_amount"));
            bill.setDiscount(rs.getDouble("discount"));
            bill.setCgst(rs.getDouble("cgst"));
            bill.setSgst(rs.getDouble("sgst"));
            bill.setGrandTotal(rs.getDouble("grand_total"));
            bill.setBillDate(rs.getTimestamp("bill_date"));
            return bill;
        });
    }

    // Mark the order as completed
    public void markOrderCompleted(int orderId) {
        String sql = "UPDATE order_master SET ord_status = 'Completed' WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }

    // Update the table status to available once the order is completed
    public void updateTableStatusToAvailable(int orderId) {
        String sql = "UPDATE dinning_table SET availability_status = 'Available' " +
                     "WHERE table_id = (SELECT table_id FROM order_master WHERE order_id = ?)";
        jdbcTemplate.update(sql, orderId);
    }

    // Get the bill by the order ID
    public Bill getBillByOrderId(int orderId) {
        String sql = "SELECT * FROM bill WHERE order_id = ?";
        List<Bill> bills = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Bill bill = new Bill();
            bill.setBillId(rs.getInt("bill_id"));
            bill.setOrderId(rs.getInt("order_id"));
            bill.setTotalAmount(rs.getDouble("total_amount"));
            bill.setDiscount(rs.getDouble("discount"));
            bill.setCgst(rs.getDouble("cgst"));
            bill.setSgst(rs.getDouble("sgst"));
            bill.setGrandTotal(rs.getDouble("grand_total"));
            bill.setBillDate(rs.getTimestamp("bill_date"));
            return bill;
        }, orderId);

        return bills.isEmpty() ? null : bills.get(0);
    }
}
