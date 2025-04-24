package com.example.demo.repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository("ordRepo")
public class OrderRepository {
	
	List<Order>list;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private boolean isValidId(String tableName, String columnName, int id) 
	{
	    String sql = "SELECT count(*) FROM " + tableName + " WHERE " + columnName + " = ?";
	    return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
	}
	
	public boolean isAddNewOrder(Order order) 
	{
	    if (!isValidId("dinning_table", "table_id", order.getTableId()) ||
	        !isValidId("staff", "staff_id", order.getStaffId()) ||
	        !isValidId("menu", "id", order.getMenuId()) ||
	        order.getQuantity() <= 0 || order.getTotalAmt().compareTo(BigDecimal.ZERO) <= 0) {
	        // Log the validation error
	        System.err.println("Error: Invalid data for new order. Order: " + order);
	        return false; // Return false to indicate failure
	    }

	    // 2. Attempt Insertion
	    int value = jdbcTemplate.update(
	        "insert into orders (table_id, staff_id, ord_date, menu_id, quantity, total_amt) values (?, ?, ?, ?, ?, ?)", //removed ord_id
	        new PreparedStatementSetter() 
	        {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException 
	            {
	                ps.setInt(1, order.getTableId());
	                ps.setInt(2, order.getStaffId());
	                ps.setDate(3, order.getOrdDate());
	                ps.setInt(4, order.getMenuId());
	                ps.setInt(5, order.getQuantity());
	                ps.setBigDecimal(6, order.getTotalAmt());
	            }
	        }
	    );
	    return value > 0; // Return true only if the update was successful
	}
	
	public List<Order> viewAllOrders()
	{
		List<Order> list = jdbcTemplate.query("select o.ord_id, o.table_id, o.staff_id, s.name as staffName, o.ord_date, o.menu_id, m.item_name as menuName, o.quantity, o.total_amt "
				+ "from Orders o "
				+ "inner join dinning_table d on o.table_id=d.table_id "
				+ "inner join staff s on s.staff_id=o.staff_id "
				+ "inner join menu m on m.id=o.menu_id", new RowMapper<Order>()
				{
					@Override
					public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
						Order order=new Order();
						order.setId(rs.getInt(1));
						order.setTableId(rs.getInt(2));
						order.setStaffId(rs.getInt(3));
						order.setStaffName(rs.getString("staffName"));
						order.setOrdDate(rs.getDate(5));
						order.setMenuId(rs.getInt(6));
						order.setMenuName(rs.getString("menuName"));
						order.setQuantity(rs.getInt(8));
						order.setTotalAmt(rs.getBigDecimal(9));
						return order;
					}
				});
		return list;
	}
	
	public Order searchOrderById(int id)
	{
	    List<Order> list = jdbcTemplate.query(
	            "SELECT o.ord_id, o.table_id, o.staff_id, s.name as staffName, o.ord_date, o.menu_id, m.item_name as menuName, o.quantity, o.total_amt " +
	            "FROM Orders o " +
	            "INNER JOIN staff s ON s.staff_id = o.staff_id " +
	            "INNER JOIN menu m ON m.id = o.menu_id " +
	            "WHERE o.ord_id = ?",
	            new Object[] {id},
	            new RowMapper<Order>()
	            {
	                @Override
	                public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
	                    Order order=new Order();
	                    order.setId(rs.getInt(1));
	                    order.setTableId(rs.getInt(2));
	                    order.setStaffId(rs.getInt(3));
	                    order.setStaffName(rs.getString(4));
	                    order.setOrdDate(rs.getDate(5));
	                    order.setMenuId(rs.getInt(6));
	                    order.setMenuName(rs.getString(7));
	                    order.setQuantity(rs.getInt(8));
	                    order.setTotalAmt(rs.getBigDecimal(9));
	                    return order;
	                }
	            });
	    return list.size()>0?list.get(0):null;
	}
	
	public boolean isDeleteOrderById(int id)
	{
		int value=jdbcTemplate.update("delete from orders where ord_id="+id);
		return value>0?true:false;
	}
	
	public boolean isUpdateOrder(Order order) 
	{
	    int value = jdbcTemplate.update(
	        "update orders set table_id=?, staff_id=?, ord_date=?, menu_id=?, quantity=?, total_amt=? where ord_id=?",
	        new PreparedStatementSetter() 
	        {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException 
	            {
	                ps.setInt(1, order.getTableId());
	                ps.setInt(2, order.getStaffId());
	                ps.setDate(3, order.getOrdDate());
	                ps.setInt(4, order.getMenuId());
	                ps.setInt(5, order.getQuantity());
	                ps.setBigDecimal(6, order.getTotalAmt());
	                ps.setInt(7, order.getId());
	            }
	        }
	    );
	    return value > 0;
	}

	public List<Order> searchOrderByPattern(String pattern) 
	{
	    List<Order> list = jdbcTemplate.query(
	        "select o.ord_id, o.table_id, o.staff_id, s.name as staffName, o.ord_date, o.menu_id, m.item_name as menuName, o.quantity, o.total_amt " +
	        "from orders o " +
	        "inner join staff s on o.staff_id = s.staff_id " +
	        "inner join menu m on m.id = o.menu_id " +
	        "where o.ord_id like ? order by o.ord_id", 
	        new Object[] { "%" + pattern + "%" },
	        new RowMapper<Order>() 
	        {
	            @Override
	            public Order mapRow(ResultSet rs, int rowNum) throws SQLException 
	            {
	                Order order = new Order();
	                order.setId(rs.getInt("ord_id"));
	                order.setTableId(rs.getInt("table_id"));
	                order.setStaffId(rs.getInt("staff_id"));
	                order.setStaffName(rs.getString("staffName"));
	                order.setOrdDate(rs.getDate("ord_date"));
	                order.setMenuId(rs.getInt("menu_id"));
	                order.setMenuName(rs.getString("menuName"));
	                order.setQuantity(rs.getInt("quantity"));
	                order.setTotalAmt(rs.getBigDecimal("total_amt"));
	                return order;
	            }
	        }
	    );
	    return list;
	}
}
