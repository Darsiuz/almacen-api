package com.almacen.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private int quantity;
    private int minStock;
    private double price;
    private String location;
}
