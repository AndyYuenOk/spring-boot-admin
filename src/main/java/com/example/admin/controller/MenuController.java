package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.mapper.MenuMapper;
import com.example.admin.mapper.UserMapper;
import com.example.admin.repository.MenuRepository;
import com.example.admin.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuMapper menuMapper;

    @GetMapping
    public Page<MenuVO> index(Pageable pageable) {
        Page<Menu> page = menuRepository.findAllByOrderByIdDesc(pageable);
        return page.map(menuMapper::toDto);
    }

    @PostMapping
    public MenuVO create(@Valid @RequestBody Menu menu) {
        System.out.println(menu);
        MenuVO menusVO = new MenuVO();
        BeanUtils.copyProperties(menuRepository.save(menu), menusVO);
        return menusVO;
    }
}
