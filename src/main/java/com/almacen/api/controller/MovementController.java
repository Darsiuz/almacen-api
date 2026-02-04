package com.almacen.api.controller;

import com.almacen.api.dto.MovementDTO;
import com.almacen.api.mapper.MovementMapper;
import com.almacen.api.model.Movement;
import com.almacen.api.service.MovementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Movement", description = "Operaciones relacionadas con movimientos de inventario")
@RequestMapping("/movement")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    // Crear movimiento (ENTRADA / SALIDA)
    @PostMapping
    @Operation(summary = "Crear un nuevo movimiento de inventario",
               description = "Crea un nuevo movimiento de inventario ya sea de tipo ENTRADA o SALIDA.")
    public ResponseEntity<MovementDTO> create(
            @RequestBody MovementDTO dto,
            Authentication authentication) {

        String email = authentication.getName();

        Movement movement = movementService.createMovement(dto, email);
        return ResponseEntity.ok(MovementMapper.toDTO(movement));
    }

    // Listar todos los movimientos
    @GetMapping
    @Operation(summary = "Listar todos los movimientos de inventario",
               description = "Obtiene una lista de todos los movimientos de inventario registrados en el sistema.")
    public ResponseEntity<List<MovementDTO>> findAll() {

        List<MovementDTO> result = movementService.findAll()
                .stream()
                .map(MovementMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // Aprobar movimiento
    @PutMapping("/{id}/approve")
    @Operation(summary = "Aprobar un movimiento de inventario",
               description = "Aprueba un movimiento de inventario pendiente, actualizando el stock correspondiente.")
    public ResponseEntity<MovementDTO> approve(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        Movement movement = movementService.approve(id, email);
        return ResponseEntity.ok(MovementMapper.toDTO(movement));
    }

    // Rechazar movimiento
    @PutMapping("/{id}/reject")
    @Operation(summary = "Rechazar un movimiento de inventario",
               description = "Rechaza un movimiento de inventario pendiente, sin actualizar el stock.")
    public ResponseEntity<MovementDTO> reject(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        Movement movement = movementService.reject(id, email);
        return ResponseEntity.ok(MovementMapper.toDTO(movement));
    }
}
