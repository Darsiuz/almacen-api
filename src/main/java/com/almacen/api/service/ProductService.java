package com.almacen.api.service;

import com.almacen.api.dto.ProductDTO;
import com.almacen.api.mapper.ProductMapper;
import com.almacen.api.model.Product;
import com.almacen.api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Listar todos
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener por ID
    public ProductDTO findById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return ProductMapper.toDTO(product);
    }

    // Crear
    public ProductDTO create(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        if (product == null) {
            throw new RuntimeException("Producto no puede ser nulo");
        }
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    // Actualizar
    public ProductDTO update(long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setQuantity(dto.getQuantity());
        product.setMinStock(dto.getMinStock());
        product.setPrice(dto.getPrice());
        product.setLocation(dto.getLocation());

        Product updated = productRepository.save(product);
        return ProductMapper.toDTO(updated);
    }

    // Eliminar
    public void delete(long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }
}