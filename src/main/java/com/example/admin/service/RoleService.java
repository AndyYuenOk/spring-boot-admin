package com.example.admin.service;

import com.example.admin.entity.Role;
import com.example.admin.exception.FieldException;
import com.example.admin.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role create(Role role) {
        if (null != roleRepository.findByAuthority(role.getAuthority())) {
            throw new FieldException(new HashMap<String, Object>() {{
                put("authority", "标识已存在");
            }});
        }
        return roleRepository.save(role);
    }
}
