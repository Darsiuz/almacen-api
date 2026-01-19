package com.almacen.api.service;

import com.almacen.api.model.Product;
import com.almacen.api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Listar todos
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Obtener por ID
    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // Crear
    public Product create(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        return productRepository.save(product);
    }

    // Actualizar
    public Product update(long id, Product updated) {
        Product product = findById(id);

        product.setName(updated.getName());
        product.setCategory(updated.getCategory());
        product.setQuantity(updated.getQuantity());
        product.setMinStock(updated.getMinStock());
        product.setPrice(updated.getPrice());
        product.setLocation(updated.getLocation());

        return productRepository.save(product);
    }

    // Eliminar
    public void delete(long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    
}
