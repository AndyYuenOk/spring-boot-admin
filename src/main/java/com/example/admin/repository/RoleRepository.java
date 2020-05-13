package com.example.admin.repository;

import com.example.admin.entity.Role;
import com.example.admin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepository extends BaseRepository<Role> {
    Page<Role> findAllByOrderByIdDesc(Pageable pageable);

    Role findByAuthority(String authority);
}
