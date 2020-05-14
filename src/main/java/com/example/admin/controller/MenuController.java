package com.example.admin.controller;

import com.example.admin.entity.Menu;
import com.example.admin.repository.MenuRepository;
import com.example.admin.service.MenuService;
import com.example.admin.util.PaginationUtil;
import com.example.admin.vo.MenuVO;
import com.hotels.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;

    private BeanUtils beanUtils = new BeanUtils();

    @GetMapping
    public ResponseEntity<?> index(Pageable pageable) {
        Page<Menu> page = menuRepository.findAllByOrderByIdDesc(pageable);

        Page<MenuVO> map = page.map(menu -> {
            MenuVO menuVO = beanUtils.getTransformer().setDefaultValueForMissingField(true).transform(menu, MenuVO.class);
            if (!menu.getPid().equals(0L)) {
                Optional<Menu> menuOptional = menuRepository.findById(menu.getPid());
                if (menuOptional.isPresent()) {
                    HashMap<String, Object> parent = new HashMap<>();
                    parent.put("id", menuOptional.get().getId());
                    parent.put("name", menuOptional.get().getName());
                    menuVO.setParent(parent);
                }
            }
            return menuVO;
        });

        return new ResponseEntity<>(PaginationUtil.toPaginationWithColumns(map, MenuVO.class), HttpStatus.OK);
    }

    @PostMapping
    public MenuVO create(@Valid @RequestBody Menu menu) {
        return beanUtils.getTransformer().transform(menuRepository.save(menu), MenuVO.class);
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
