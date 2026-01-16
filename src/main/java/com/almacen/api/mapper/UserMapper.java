package com.almacen.api.mapper;

import com.almacen.api.dto.UserDTO;
import com.almacen.api.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                user.isActive()
        );
    }
}
