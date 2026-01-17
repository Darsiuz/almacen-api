package com.almacen.api.mapper;

import com.almacen.api.dto.UserDTO;
import com.almacen.api.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getName());
        dto.setActive(user.isActive());
        return dto;
    }

}
