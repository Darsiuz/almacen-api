package com.almacen.api.mapper;

import com.almacen.api.dto.MovementDTO;
import com.almacen.api.model.Movement;

public class MovementMapper {

    public static MovementDTO toDTO(Movement movement) {
        return new MovementDTO(
                movement.getId(),
                movement.getProduct().getName(),
                movement.getType(),
                movement.getQuantity(),
                movement.getStatus(),
                movement.getUser().getName(),
                movement.getDate()
        );
    }
}
