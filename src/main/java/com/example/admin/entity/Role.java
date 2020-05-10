package com.example.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"menus", "users"}, callSuper = true)
@ToString(exclude = {"menus", "users"})
@JsonIgnoreProperties({"menus", "users"})
public class Role extends BaseEntity implements GrantedAuthority {
    @NotNull
    private String name;

    private String authority;

    @ManyToMany
    @JoinTable(name = "role_menu",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")
            })
    private Set<Menu> menus;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}
