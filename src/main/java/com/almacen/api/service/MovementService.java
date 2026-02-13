package com.almacen.api.service;

import com.almacen.api.dto.MovementDTO;
import com.almacen.api.model.Movement;
import com.almacen.api.model.Product;
import com.almacen.api.model.User;
import com.almacen.api.repository.MovementRepository;
import com.almacen.api.repository.ProductRepository;
import com.almacen.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SystemConfigService systemConfigService;

    public MovementService(
            MovementRepository movementRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            SystemConfigService systemConfigService) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.systemConfigService = systemConfigService;
    }

    private void updateStock(Product product, String type, int quantity) {

        if (!"ENTRADA".equalsIgnoreCase(type)
                && product.getQuantity() < quantity) {
            throw new RuntimeException("Stock insuficiente");
        }

        if ("ENTRADA".equalsIgnoreCase(type)) {
            product.setQuantity(product.getQuantity() + quantity);
        } else {
            product.setQuantity(product.getQuantity() - quantity);
        }
    }

    // Crear movimiento (ENTRADA / SALIDA)
    @Transactional
    public Movement createMovement(MovementDTO dto, String email) {
        Long productId = dto.getProductId();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (productId == null)
            throw new IllegalArgumentException("El id del producto no puede ser null");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Movement movement = new Movement();
        movement.setProduct(product);
        movement.setType(dto.getType());
        movement.setQuantity(dto.getQuantity());
        movement.setReason(dto.getReason());
        // movement.setStatus("PENDIENTE");
        movement.setUser(user);
        movement.setDate(LocalDateTime.now());

        // Una pequeña modificacion para poder hacer funcionar opcion de auto-aprobacion
        boolean autoApprove = systemConfigService.getEntity().isAutoApproveMovements();
        if (autoApprove) {
            movement.setStatus("APROBADO");
            updateStock(product, dto.getType(), dto.getQuantity());
        } else {
            movement.setStatus("PENDIENTE");
        }
        // -------------------------------------------------------
        return movementRepository.save(movement);
    }

    // Listar movimientos
    public List<Movement> findAll() {
        return movementRepository.findAll();
    }

    // Aprobar movimiento
    @Transactional
    public Movement approve(Long id, String email) {
        if (id == null)
            throw new IllegalArgumentException("El id del movimiento no puede ser null");
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        movement.setStatus("APROBADO");
        movement.setUser(manager);
        movement.setDate(LocalDateTime.now());

        // Actualizar stock
        Product product = movement.getProduct();
        updateStock(product, movement.getType(), movement.getQuantity());

        return movementRepository.save(movement);
    }

    // Rechazar movimiento
    public Movement reject(Long id, String email) {
        if (id == null)
            throw new IllegalArgumentException("El id del movimiento no puede ser null");
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
