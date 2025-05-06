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

    // ✅ Updated to fetch table_id directly from order_master (or bill if stored there)
    public int getTableNumber(int orderId) {
        String sql = "SELECT table_id FROM order_master WHERE order_id = ?";
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
        // Lock the order row to prevent concurrent insertions for same order
        String lockSql = "SELECT * FROM order_master WHERE order_id = ? FOR UPDATE";
        jdbcTemplate.queryForList(lockSql, bill.getOrderId());

        // Now check if bill already exists
        Bill existingBill = getBillByOrderId(bill.getOrderId());
        if (existingBill != null) {
            return existingBill.getBillId();
        }

        // Insert new bill
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

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    // ✅ UPDATED getAllBills() with staff name, table ID, and items
    public List<Bill> getAllBills() {
        String billSql = "SELECT * FROM bill";

        return jdbcTemplate.query(billSql, (rs, rowNum) -> {
            Bill bill = new Bill();
            bill.setBillId(rs.getInt("bill_id"));
            bill.setOrderId(rs.getInt("order_id"));
            bill.setTotalAmount(rs.getDouble("total_amount"));
            bill.setDiscount(rs.getDouble("discount"));
            bill.setCgst(rs.getDouble("cgst"));
            bill.setSgst(rs.getDouble("sgst"));
            bill.setGrandTotal(rs.getDouble("grand_total"));
            bill.setBillDate(rs.getTimestamp("bill_date"));

            int orderId = rs.getInt("order_id");

            try {
                bill.setStaffname(getWaiterName(orderId));
            } catch (Exception e) {
                bill.setStaffname("N/A");
            }

            try {
                bill.setTableId(getTableNumber(orderId));
            } catch (Exception e) {
                bill.setTableId(0);
            }

            bill.setItems(getOrderItems(orderId));

            return bill;
        });
    }

    private List<OrderItem> getItemsByOrderId(int orderId) {
        String sql = "SELECT m.item_name, od.quantity, od.total_amt " +
                     "FROM order_details od " +
                     "JOIN menu m ON od.menu_id = m.menu_id " +
                     "WHERE od.order_id = ?";

        return jdbcTemplate.query(sql, new Object[]{orderId}, (rs, rowNum) -> {
            OrderItem item = new OrderItem();
            item.setItemName(rs.getString("item_name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setTotalAmt(rs.getBigDecimal("total_amt"));
            return item;
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

    // Get the bill by the order ID and include table number
    public Bill getBillByOrderId(int orderId) {
        String sql = "SELECT b.*, om.table_id " +
                     "FROM bill b " +
                     "JOIN order_master om ON b.order_id = om.order_id " +
                     "WHERE b.order_id = ?";

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
            bill.setTableId(rs.getInt("table_id"));  // ✅ setting table ID here
            return bill;
        }, orderId);

        return bills.isEmpty() ? null : bills.get(0);
    }
}
