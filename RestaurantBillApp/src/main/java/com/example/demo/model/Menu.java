package com.example.demo.model;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Menu {
	private int id;
	private String name;
	private int categoryId;
	private String categoryName;
	private BigDecimal price;
	private String description;
}
