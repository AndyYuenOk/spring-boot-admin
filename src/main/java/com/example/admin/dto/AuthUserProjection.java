package com.example.admin.dto;

import com.example.admin.entity.Role;

import java.util.Set;

public interface AuthUserProjection {
    Long getId();

    String getUsername();

    String getNickname();

    String getSex();

    Set<Role> getAuthorities();
}