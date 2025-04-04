package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;


@Service("catService")
public class CategoryService {
	
	@Autowired
	CategoryRepository catRepo;
}
