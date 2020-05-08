package com.example.admin.repository;

import com.example.admin.entity.Role;
import com.example.admin.dto.RoleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepository extends BaseRepository<Role> {
    Page<RoleProjection> findAllProjectedBy(Pageable pageable);
}
