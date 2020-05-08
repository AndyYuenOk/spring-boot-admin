package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private MenuRepository menuRepository;

    @GetMapping("/menus")
    public Set<Menu> getMenus() {
        return menuRepository.findByIsMenu(true);
    }
}
