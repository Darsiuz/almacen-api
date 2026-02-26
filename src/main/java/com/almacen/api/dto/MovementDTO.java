package com.almacen.api.dto;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String type;
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private int quantity;
    private String reason;
    @NotBlank(message = "El estado del movimiento es necesario")
    private String status;
    private String user;
    private LocalDateTime date;
}
