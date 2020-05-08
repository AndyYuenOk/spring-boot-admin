package com.example.admin.controller;

import com.example.admin.dto.UserDto;
import com.example.admin.entity.User;
import com.example.admin.dto.UserProjection;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户控制器")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ApiOperation("用户列表")
    @GetMapping
    public Page<UserProjection> index(@RequestParam(defaultValue = "0") int page) {
        return userRepository.findAllByOrderByIdDesc(PageRequest.of(page, 10));
    }

    @ApiOperation("创建用户")
    @PostMapping
    public UserDto create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
