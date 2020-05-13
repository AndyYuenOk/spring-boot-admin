package com.example.admin.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
public class RoleDTO implements Serializable {
    @NotBlank
    private String name;

    @NotBlank
    private String authority;

    @JsonSetter("menu_ids")
    @NotNull
    private Set<Long> menuIds;
}
