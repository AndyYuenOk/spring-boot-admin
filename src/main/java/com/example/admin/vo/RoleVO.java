package com.example.admin.vo;

import com.example.admin.entity.Role;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class RoleVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("标识")
    private String authority;

    private Set<MenuVO> menus;
}
