package com.example.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MenuVO {
    @ApiModelProperty("ID")
    private Long id;

    private Map<String, Object> parent;

    private Long pid;

    private Boolean isMenu;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("链接")
    private String link;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("排序")
    private Integer sort;
}
