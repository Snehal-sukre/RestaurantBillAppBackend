package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Menu;
import com.example.demo.repository.MenuRepository;

@Service("menuService")
public class MenuService {
	
	@Autowired
	private MenuRepository menuRepo;
	
	public boolean isAddNewMenu(Menu menu)
	{
		return menuRepo.isAddNewMenu(menu);
	}
	
	public List<Menu> viewAllMenus()
	{
		return menuRepo.viewAllMenus();
	}
	
	public Menu searchMenuById(int id)
	{
		return menuRepo.searchMenuById(id);
	}
	
	public boolean isDeleteMenuById(int id)
	{
		return menuRepo.isDeleteMenuById(id);
	}
	
	public boolean isUpdateMenu(Menu menu)
	{
		return menuRepo.isUpdateMenu(menu);
	}
}
