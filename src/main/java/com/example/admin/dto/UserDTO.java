package com.example.admin.dto;

import com.example.admin.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserDTO implements Serializable {
    @NotBlank(groups = {Default.class, BaseEntity.Update.class})
    @Size(min = 2, max = 10, groups = {Default.class, BaseEntity.Update.class})
    private String username;

    @NotBlank
    @Size(min = 6, max = 20, groups = {Default.class, BaseEntity.Update.class})
    private String password;

    @JsonSetter("role_ids")
    private Set<Long> roleIds;
}
