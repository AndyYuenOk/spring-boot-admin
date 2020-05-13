package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.repository.MenuRepository;
import com.example.admin.service.MenuService;
import com.example.admin.util.PaginationUtil;
import com.example.admin.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<?> index(Pageable pageable) {
        Page<Menu> page = menuRepository.findAllByOrderByIdDesc(pageable);

        Page<MenuVO> map = page.map(menu -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);
            if (!menu.getPid().equals(0L)) {
                Menu menu1 = menuRepository.findById(menu.getPid()).orElse(null);
                if (menu1 != null) {
                    HashMap<String, Object> parent = new HashMap<>();
                    parent.put("id", menu1.getId());
                    parent.put("name", menu1.getName());
                    menuVO.setParent(parent);
                }
            }
            return menuVO;
        });

        return new ResponseEntity<>(PaginationUtil.toPaginationWithColumns(map, MenuVO.class), HttpStatus.OK);
    }

    @PostMapping
    public MenuVO create(@Valid @RequestBody Menu menu) {
        System.out.println(menu);
        MenuVO menusVO = new MenuVO();
        BeanUtils.copyProperties(menuRepository.save(menu), menusVO);
        return menusVO;
    }

    @PutMapping("/{id}")
    public Menu update(@PathVariable Long id, @Valid @RequestBody Menu menu) {
        menu.setId(id);
        return menuService.update(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        menuRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
