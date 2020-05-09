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
import io.swagger.annotations.ApiModelProperty;
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

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Arrays;
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
    public ResponseEntity<?> index(Pageable pageable) throws NoSuchFieldException {
        Page<User> page = userRepository.findAllByOrderByIdDesc(pageable);
        Field id = UserVO.class.getDeclaredField("id");
        System.out.println(id.getAnnotation(ApiModelProperty.class).value());
        return new ResponseEntity<>(PaginationUtil.toPagination(page, UserVO.class), HttpStatus.OK);
    }

    @ApiOperation("创建用户")
    @PostMapping
    public UserDTO create(@Validated({BaseEntity.Create.class}) @RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setAuthorities(userDTO, user);
        System.out.println(user);

        return userService.create(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User user = new User();
        user.setId(id);
        BeanUtils.copyProperties(userDTO, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setAuthorities(userDTO, user);

        System.out.println(user);
        return userRepository.save(user);
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