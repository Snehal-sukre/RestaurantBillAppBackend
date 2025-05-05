package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DailyOrdersSummary;
import com.example.demo.model.TopOrderedItems;

@Repository("reportRepo")
public class ReportRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<DailyOrdersSummary> getDailyOrders()
	{
		String sql = """
	            SELECT 
	                ord_date AS orderDate,
	                COUNT(order_id) AS totalOrders,
	                SUM(total_amt) AS totalAmount
	            FROM order_master
	            GROUP BY ord_date
	            ORDER BY ord_date DESC
	        """;
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DailyOrdersSummary dailyOrd = new DailyOrdersSummary();
            dailyOrd.setOrderDate(rs.getDate("orderDate").toLocalDate());
            dailyOrd.setTotalOrders(rs.getInt("totalOrders"));
            dailyOrd.setTotalAmount(rs.getBigDecimal("totalAmount"));
            return dailyOrd;
        });
    }
	
	public List<TopOrderedItems> getTopOrderedItems() {
	    String sql = """
	        SELECT 
	            m.item_name AS menuName,
	            SUM(oi.quantity) AS totalQuantity,
	            SUM(oi.total_amt) AS totalRevenue
	        FROM order_items oi
	        JOIN menu m ON oi.menu_id = m.id
	        GROUP BY m.item_name
	        ORDER BY totalQuantity DESC
	        LIMIT 7
	    """;

	    return jdbcTemplate.query(sql, (rs, rowNum) -> {
	        TopOrderedItems item = new TopOrderedItems();
	        item.setItemName(rs.getString(1));
	        item.setTotalQty(rs.getInt(2));
	        item.setTotalAmt(rs.getBigDecimal(3));
	        return item;
	    });
	}

}
