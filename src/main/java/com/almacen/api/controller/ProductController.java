package com.almacen.api.controller;

import com.almacen.api.dto.ProductDTO;
import com.almacen.api.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Gestor de Productos", description = "Endpoints para la gestion de productos")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET - Listar productos
    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos disponibles en el almacen")
    public List<ProductDTO> getAll() {
        return productService.findAll();
    }

    // GET - Producto por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene los detalles de un producto especifico mediante su ID")
    public ProductDTO getById(@PathVariable long id) {
        return productService.findById(id);
    }

    // POST - Crear producto
    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el almacen con los datos proporcionados")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductDTO product) {
        return productService.create(product);
    }

    // PUT - Actualizar producto
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los datos de un producto existente mediante su ID")
    public ProductDTO update(
            @PathVariable long id,
            @RequestBody ProductDTO product
    ) {
        return productService.update(id, product);
    }

    // DELETE - Eliminar producto
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del almacen mediante su ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productService.delete(id);
    }
}
