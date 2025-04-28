/*package com.example.demo.repository;

import com.example.demo.model.OrderMaster;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class OrderMasterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(OrderMaster orderMaster) {
        String query = "INSERT INTO order_master (table_id, staff_id, ord_date, total_amt, ord_status) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(query, ps -> {
            ps.setInt(1, orderMaster.getTableId());
            ps.setInt(2, orderMaster.getStaffId());
            ps.setDate(3, new java.sql.Date(orderMaster.getOrdDate().getTime()));
            ps.setBigDecimal(4, orderMaster.getTotalAmt());
            ps.setString(5, orderMaster.getOrdStatus());
        });
    }
}  */
