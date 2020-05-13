package com.example.admin.controller;

import com.example.admin.dto.UserDTO;
import com.example.admin.entity.BaseEntity;
import com.example.admin.entity.Role;
import com.example.admin.entity.User;
import com.example.admin.repository.RoleRepository;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import com.example.admin.util.PaginationUtil;
import com.example.admin.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Api(tags = "用户控制器")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @ApiOperation("用户列表")
    @GetMapping
    public ResponseEntity<?> index(Pageable pageable) {
        Page<User> page = userRepository.findAllByOrderByIdDesc(pageable);
        return new ResponseEntity<>(PaginationUtil.toPaginationWithColumns(page, UserVO.class), HttpStatus.OK);
    }

    @ApiOperation("创建用户")
    @PostMapping
    public UserVO create(@Validated @RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setAuthorities(userDTO, user);
        System.out.println(user);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userService.create(user), userVO);

        return userVO;
    }

//    @GetMapping("/{id}")
//    public UserVO show(@PathVariable Long id) {
//        return userRepository.findById(id);
//    }

    @PutMapping("/{id}")
    public UserVO update(@PathVariable Long id, @Validated({BaseEntity.Update.class}) @RequestBody UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("用户不存在");
        });

        if (userDTO.getPassword() == null) {
            userDTO.setPassword(user.getPassword());
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        BeanUtils.copyProperties(userDTO, user);
        setAuthorities(userDTO, user);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userRepository.save(user), userVO);

        return userVO;
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void setAuthorities(UserDTO userDTO, User user) {
        List<Role> roles = roleRepository.findAllById(userDTO.getRoleIds());
        user.setAuthorities(new HashSet<>(roles));
    }
}