package com.example.admin.service;

import com.example.admin.entity.Menu;
import com.example.admin.exception.FieldException;
import com.example.admin.repository.MenuRepository;
import com.example.admin.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Menu> getMenus() {
        return menuRepository.findAll();
    }

    public Menu create(Menu newMenu) {
        Menu sourceMenu = menuRepository.findById(newMenu.getId()).orElseThrow(() -> {
            throw new FieldException(new HashMap<String, Object>() {{
                put("username", "菜单不存在");
            }});
        });

        BeanUtils.copyProperties(newMenu, sourceMenu);

        return menuRepository.save(sourceMenu);
    }

    public Menu update(Menu newMenu) {
        Menu sourceMenu = menuRepository.findById(newMenu.getId()).orElseThrow(() -> {
            throw new RuntimeException("菜单不存在");
        });

        if (newMenu.getPid().equals(newMenu.getId())) {
            throw new FieldException(new HashMap<String, Object>() {{
                put("pid", "父级不能是自己");
            }});
        }

        if (!newMenu.getPid().equals(0L)) {
            menuRepository.findById(newMenu.getPid()).orElseThrow(() -> {
                throw new FieldException(new HashMap<String, Object>() {{
                    put("pid", "父级菜单不存在");
                }});
            });

            if (getChildIds(newMenu.getId()).contains(newMenu.getPid())) {
                throw new FieldException(new HashMap<String, Object>() {{
                    put("pid", "父级不能是子菜单");
                }});
            }
        }

        BeanUtils.copyProperties(newMenu, sourceMenu);

        return menuRepository.save(sourceMenu);
    }

    private List<Long> getChildIds(Long id) {
        List<Long> childIds = menuRepository
                .findAllByPid(id)
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());

        if (!childIds.isEmpty()) {
            for (Long childId : childIds) {
                childIds.addAll(getChildIds(childId));
            }
        }

        return childIds;
    }
}
