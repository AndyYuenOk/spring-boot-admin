package com.example.admin.mapper;

import com.example.admin.dto.UserDTO;
import com.example.admin.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, User> {
}
