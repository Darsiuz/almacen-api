package com.almacen.api.controller;

import com.almacen.api.model.Product;
import com.almacen.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET - Listar productos
    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    // GET - Producto por ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable long id) {
        return productService.findById(id);
    }

    // POST - Crear producto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    // PUT - Actualizar producto
    @PutMapping("/{id}")
    public Product update(
            @PathVariable long id,
            @RequestBody Product product
    ) {
        return productService.update(id, product);
    }

    // DELETE - Eliminar producto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productService.delete(id);
    }
}
