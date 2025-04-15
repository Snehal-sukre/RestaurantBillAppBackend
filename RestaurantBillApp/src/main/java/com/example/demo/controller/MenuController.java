package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.MenuNotFoundException;
import com.example.demo.model.Menu;
import com.example.demo.service.MenuService;

@RestController
public class MenuController {
	
	@Autowired
	MenuService menuService;
	
	@PostMapping("/addmenu")
	public String addMenu(@RequestBody Menu menu)
	{
		boolean b=menuService.isAddNewMenu(menu);
		if(b)
		{
			return "Menu Added Successfully";
		}
		else
		{
			return "Menu Already Exists";
		}
	}
	
	@GetMapping("/viewmenus")
	public List<Menu> viewAllMenus()
	{
		List<Menu> list=menuService.viewAllMenus();
		if(list.size()!=0)
		{
			return list;
		}
		else
		{
			throw new MenuNotFoundException("There is No Menus Present in Database");
		}
	}
	
	@GetMapping("/searchMenuById/{menuid}")
	public Menu searchMenuById(@PathVariable("menuid") Integer id)
	{
		Menu menu=menuService.searchMenuById(id);
		if(menu!=null)
		{
			return menu;
		}
		else
		{
			throw new MenuNotFoundException("Menu Not Found Using Id: "+id);
		}
	}
	
	@DeleteMapping("/deleteMenuById/{menuid}")
	public String deleteMenuById(@PathVariable("menuid") Integer id)
	{
		boolean b=menuService.isDeleteMenuById(id);
		if(b)
		{
			return "Menu Deleted Successfully";
		}
		else
		{
			throw new MenuNotFoundException("Menu Not Found Using Id: "+id);
		}
	}
	
	@PutMapping("/updateMenu")
	public String updateMenu(@RequestBody Menu menu)
	{
		boolean b=menuService.isUpdateMenu(menu);
		if(b)
		{
			return "Menu Record Updated Successfully";
		}
		else
		{
			throw new MenuNotFoundException("Menu Not Found Using Id: "+menu.getId());
		}
	}
}
