package com.example.admin.service;

import com.example.admin.dto.UserDTO;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username, User.class);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public UserDTO create(User user) {
        if (null != userRepository.findByUsername(user.getUsername(), User.class)) {
            throw new RuntimeException("用户名已存在");
        }
        return userMapper.toDto(userRepository.save(user));
    }
}
