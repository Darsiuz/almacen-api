package com.almacen.api.controller;

import com.almacen.api.dto.MovementDTO;
import com.almacen.api.mapper.MovementMapper;
import com.almacen.api.model.Movement;
import com.almacen.api.service.MovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movement")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    // Crear movimiento (ENTRADA / SALIDA)
    @PostMapping
    public ResponseEntity<MovementDTO> create(
            @RequestBody MovementDTO dto,
            Authentication authentication) {

        String email = authentication.getName();

        Movement movement = movementService.createMovement(dto, email);
        return ResponseEntity.ok(MovementMapper.toDTO(movement));
    }

    // Listar todos los movimientos
    @GetMapping
    public ResponseEntity<List<MovementDTO>> findAll() {

        List<MovementDTO> result = movementService.findAll()
                .stream()
                .map(MovementMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // Aprobar movimiento
    @PutMapping("/{id}/approve")
    public ResponseEntity<MovementDTO> approve(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        Movement movement = movementService.approve(id, email);
        return ResponseEntity.ok(MovementMapper.toDTO(movement));
    }

    // Rechazar movimiento
    @PutMapping("/{id}/reject")
    public ResponseEntity<MovementDTO> reject(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        Movement movement = movementService.reject(id, email);
        return ResponseEntity.ok(MovementMapper.toDTO(movement));
    }
}
