package com.example.admin.dto;

import com.example.admin.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserDTO implements Serializable {
    @NotBlank
    @Size(min = 2, max = 10)
    private String username;

    @NotBlank(groups = BaseEntity.Create.class)
    @Size(min = 6)
    private String password;

    @JsonSetter("role_ids")
    private Set<Long> roleIds;
}
