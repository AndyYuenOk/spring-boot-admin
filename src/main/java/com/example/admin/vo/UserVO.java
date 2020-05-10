package com.example.admin.vo;

import com.example.admin.annotation.ColumnKey;
import com.example.admin.entity.Role;
import com.example.admin.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("性别")
    @ColumnKey("sex.name")
    private User.SexEnum sex;

    @JsonSetter("roles")
    private Set<Role> authorities;

    @ApiModelProperty("创建日期")
    @JsonSetter("create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
}
