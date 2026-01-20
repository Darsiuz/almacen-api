package com.almacen.api.controller;

import com.almacen.api.dto.IncidentDTO;
import com.almacen.api.mapper.IncidentMapper;
import com.almacen.api.model.Incident;
import com.almacen.api.service.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/incident")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // Crear incidencia
    @PostMapping
    public ResponseEntity<IncidentDTO> create(
            @RequestBody IncidentDTO dto,
            Authentication authentication) {

        String email = authentication.getName();

        Incident incident = incidentService.create(dto, email);
        return ResponseEntity.ok(IncidentMapper.toDTO(incident));
    }

    // Listar incidencias
    @GetMapping
    public ResponseEntity<List<IncidentDTO>> findAll() {

        List<IncidentDTO> result = incidentService.findAll()
                .stream()
                .map(IncidentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // Resolver incidencia
    @PutMapping("/{id}/resolve")
    public ResponseEntity<IncidentDTO> resolve(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        Incident incident = incidentService.resolve(id, email);
        return ResponseEntity.ok(IncidentMapper.toDTO(incident));
    }

    // Rechazar incidencia
    @PutMapping("/{id}/reject")
    public ResponseEntity<IncidentDTO> reject(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        Incident incident = incidentService.reject(id, email);
        return ResponseEntity.ok(IncidentMapper.toDTO(incident));
    }
}
