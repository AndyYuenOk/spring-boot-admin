package com.example.admin.mapper;

import com.example.admin.dto.UserDto;
import com.example.admin.entity.Menu;
import com.example.admin.entity.User;
import com.example.admin.vo.MenuVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends BaseMapper<MenuVO, Menu> {
}
