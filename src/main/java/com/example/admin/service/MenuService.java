package com.example.admin.service;

import com.example.admin.entity.Menu;
import com.example.admin.repository.MenuRepository;
import com.example.admin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MenuService {
//    @Autowired
//    private MenuRepository menuRepository;

    @Autowired
    private RoleRepository roleRepository;

//    public Set<Menu> getMenus() {
//        Set<Menu> menus = menuRepository.findAll();
//        for (Menu menu : menus) {
//            menu.setRoles(roleRepository.findByMenu(menu.getId()));
//        }
//        return menus;
//    }
}
