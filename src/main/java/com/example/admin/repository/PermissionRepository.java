package com.example.admin.repository;

import com.example.admin.entity.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, Long> {
}
