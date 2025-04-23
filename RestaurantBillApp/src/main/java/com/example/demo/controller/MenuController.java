package com.example.demo.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exceptions.MenuNotFoundException;
import com.example.demo.model.Menu;
import com.example.demo.service.MenuService;

@RestController
@CrossOrigin("http://localhost:5173")
public class MenuController {

    @Autowired
    MenuService menuService;

    private final String UPLOAD_DIR = "src/main/resources/static/Images/"; // Define where to save images

    @PostMapping("/addmenu")
    public String addMenu(
            @RequestParam("name") String name,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file) {

        try {
            // Save the image to the server
            String imageName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, imageName);
            Files.copy(file.getInputStream(), filePath);

            Menu menu = new Menu();
            menu.setName(name);
            menu.setCategoryId(categoryId);
            menu.setPrice(price);
            menu.setDescription(description);
            menu.setImage("/images/" + imageName); // Save the relative path to the image

            boolean b = menuService.isAddNewMenu(menu);
            if (b) {
                return "Menu Added Successfully";
            } else {
                return "Menu Already Exists";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload image and add menu.";
        }
    }

    @GetMapping("/viewmenus")
    public List<Menu> viewAllMenus() {
        List<Menu> list = menuService.viewAllMenus();
        if (list.size() != 0) {
            return list;
        } else {
            throw new MenuNotFoundException("There is No Menus Present in Database");
        }
    }

    @GetMapping("/searchMenuById/{menuid}")
    public Menu searchMenuById(@PathVariable("menuid") Integer id) {
        Menu menu = menuService.searchMenuById(id);
        if (menu != null) {
            return menu;
        } else {
            throw new MenuNotFoundException("Menu Not Found Using Id: " + id);
        }
    }

    @DeleteMapping("/deleteMenuById/{menuid}")
    public String deleteMenuById(@PathVariable("menuid") Integer id) {
        boolean b = menuService.isDeleteMenuById(id);
        if (b) {
            return "Menu Deleted Successfully";
        } else {
            throw new MenuNotFoundException("Menu Not Found Using Id: " + id);
        }
    }

    @PutMapping("/updateMenu")
    public String updateMenu(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("categoryId") int categoryId,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("description") String description,
                             @RequestParam(value = "image", required = false) MultipartFile file) { // Image is optional for update
        try {
            Menu existingMenu = menuService.searchMenuById(id);
            if (existingMenu == null) {
                throw new MenuNotFoundException("Menu Not Found Using Id: " + id);
            }

            existingMenu.setName(name);
            existingMenu.setCategoryId(categoryId);
            existingMenu.setPrice(price);
            existingMenu.setDescription(description);

            if (file != null && !file.isEmpty()) {
                // Save the new image
                String imageName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, imageName);
                Files.copy(file.getInputStream(), filePath);
                existingMenu.setImage("/images/" + imageName);
            }

            boolean b = menuService.isUpdateMenu(existingMenu);
            if (b) {
                return "Menu Record Updated Successfully";
            } else {
                return "Failed to update menu.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload image and update menu.";
        } catch (MenuNotFoundException e) {
            throw e;
        }
    }

    @GetMapping("/searchMenu/{pattern}")
    public List<Menu> searchMenuByPattern(@PathVariable("pattern") String pattern) {
        List<Menu> list = menuService.searchMenuByPattern(pattern);
        if (list.size() != 0) {
            return list;
        } else {
            throw new MenuNotFoundException("Menu Not Found");
        }
    }
}