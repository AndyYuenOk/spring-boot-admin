package com.example.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"roles"})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pid = 0L;

    @Column(name = "is_menu")
    private Boolean isMenu = true;

    @NotBlank
    private String name;
    private String link = "";
    @NotBlank
    private String path;

    @NotNull
    @Size(max = 10)
    private String icon = "";
    private Integer sort = 0;

    @JsonIgnore
    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles;
}
