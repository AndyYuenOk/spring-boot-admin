package com.example.admin.controller;

import com.example.admin.dto.RoleProjection;
import com.example.admin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

//    @GetMapping
//    public Page<RoleProjection> index(@RequestParam(defaultValue = "0") int page) {
//        return roleRepository.findAllProjectedBy(PageRequest.of(page, 20));
//    }
}
