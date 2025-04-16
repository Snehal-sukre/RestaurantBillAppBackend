package com.example.demo.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Staff {
	private int id;
	private String name;
	private String contact;
	private BigDecimal salary;
	private String email;
}
