package com.example.admin.vo;

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

    private String username;

    private User.SexEnum sex;

    @JsonSetter("roles")
    private Set<Role> authorities;

    @JsonSetter("create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
}
