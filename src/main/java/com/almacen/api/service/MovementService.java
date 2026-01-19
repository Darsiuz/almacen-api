package com.almacen.api.service;

import com.almacen.api.dto.MovementDTO;
import com.almacen.api.model.Movement;
import com.almacen.api.model.Product;
import com.almacen.api.model.User;
import com.almacen.api.repository.MovementRepository;
import com.almacen.api.repository.ProductRepository;
import com.almacen.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public MovementService(
            MovementRepository movementRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Crear movimiento (ENTRADA / SALIDA)
    public Movement createMovement(MovementDTO dto, String email) {
        Long productId = dto.getProductId();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (productId == null) throw new IllegalArgumentException("El id del producto no puede ser null");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Movement movement = new Movement();
        movement.setProduct(product);
        movement.setType(dto.getType());
        movement.setQuantity(dto.getQuantity());
        movement.setReason(dto.getReason());
        movement.setStatus("PENDIENTE");
        movement.setUser(user);
        movement.setDate(LocalDateTime.now());

        return movementRepository.save(movement);
    }

    // Listar movimientos
    public List<Movement> findAll() {
        return movementRepository.findAll();
    }

    // Aprobar movimiento
    public Movement approve(Long id, String email) {
        if (id == null) throw new IllegalArgumentException("El id del movimiento no puede ser null");
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        movement.setStatus("APROBADO");
        movement.setUser(manager);
        movement.setDate(LocalDateTime.now());

        // Actualizar stock
        Product product = movement.getProduct();
        if ("ENTRADA".equalsIgnoreCase(movement.getType())) {
            product.setQuantity(product.getQuantity() + movement.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() - movement.getQuantity());
        }

        return movementRepository.save(movement);
    }

    // Rechazar movimiento
    public Movement reject(Long id, String email) {
        if (id == null) throw new IllegalArgumentException("El id del movimiento no puede ser null");
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        movement.setStatus("RECHAZADO");
        movement.setUser(manager);
        movement.setDate(LocalDateTime.now());

        return movementRepository.save(movement);
    }
}
