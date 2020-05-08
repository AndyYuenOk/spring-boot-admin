package com.example.admin.mapper;

import com.example.admin.dto.UserDto;
import com.example.admin.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}
