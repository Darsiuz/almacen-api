package com.almacen.api.mapper;

import com.almacen.api.dto.ProductDTO;
import com.almacen.api.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getQuantity(),
                product.getMinStock(),
                product.getPrice(),
                product.getLocation()
        );
    }

    public static Product toEntity(ProductDTO dto) {
        return new Product(
                dto.getId(),
                dto.getName(),
                dto.getCategory(),
                dto.getQuantity(),
                dto.getMinStock(),
                dto.getPrice(),
                dto.getLocation()
        );
    }
}