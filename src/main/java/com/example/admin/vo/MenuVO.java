package com.example.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MenuVO {
    private Long id;
    private Map<String, Object> parent;
    private Long pid;
    private Boolean isMenu;
    private String name;
    private String link;
    private String path;
    private String icon;
    private Integer sort;
}
