package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("标识")
    private String authority;
}
