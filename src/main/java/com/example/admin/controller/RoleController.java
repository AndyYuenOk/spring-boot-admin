package com.example.admin.controller;

import com.example.admin.entity.Role;
import com.example.admin.repository.RoleRepository;
import com.example.admin.util.PaginationUtil;
import com.example.admin.vo.RoleVO;
import com.example.admin.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<?> index(Pageable pageable) {
        Page<Role> page = roleRepository.findAllByOrderByIdDesc(pageable);
        return new ResponseEntity<>(PaginationUtil.toPaginationWithColumns(page, RoleVO.class), HttpStatus.OK);
    }
}
