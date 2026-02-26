package com.almacen.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank(message = "El nombre del producto es necesario")
    @Size(max = 100, message = "El nombre del producto no puede exceder los 100 caracteres")
    private String name;
    @NotBlank(message = "La categoría del producto es necesaria")
    private String category;
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private int quantity;
    @PositiveOrZero(message = "El stock minimo no puede ser negativo")
    private int minStock;
    @Positive(message = "El precio debe ser un valor positivo")
    private double price;
    @Size(max = 150, message = "La ubicación no puede exceder los 150 caracteres")
    private String location;
}
