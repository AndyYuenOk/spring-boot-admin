package com.example.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Permission {
    @Id
    private Long id;
    private String name;

    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
