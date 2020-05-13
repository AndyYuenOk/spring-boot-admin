package com.example.admin.controller;

import com.example.admin.dto.RoleDTO;
import com.example.admin.entity.Menu;
import com.example.admin.entity.Role;
import com.example.admin.repository.MenuRepository;
import com.example.admin.repository.RoleRepository;
import com.example.admin.service.RoleService;
import com.example.admin.util.PaginationUtil;
import com.example.admin.vo.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping
    public ResponseEntity<?> index(Pageable pageable) {
        Page<Role> page = roleRepository.findAllByOrderByIdDesc(pageable);
        return new ResponseEntity<>(PaginationUtil.toPaginationWithColumns(page, RoleVO.class), HttpStatus.OK);
    }

    @PostMapping
    public RoleVO create(@Validated @RequestBody RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);

        setMenus(roleDTO, role);
        System.out.println(role);

        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(roleService.create(role), roleVO);

        return roleVO;
    }

    @PutMapping("/{id}")
    public RoleVO update(@PathVariable Long id, @Validated @RequestBody RoleDTO roleDTO) {
        Role role = roleRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("角色不存在");
        });

        BeanUtils.copyProperties(roleDTO, role);
        setMenus(roleDTO, role);

        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(roleRepository.save(role), roleVO);

        return roleVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void setMenus(RoleDTO roleDTO, Role role) {
        List<Menu> menus = menuRepository.findAllById(roleDTO.getMenuIds());
        role.setMenus(new HashSet<>(menus));
    }
}
