package com.almacen.api.service;

import com.almacen.api.dto.IncidentDTO;
import com.almacen.api.model.Incident;
import com.almacen.api.model.Product;
import com.almacen.api.model.User;
import com.almacen.api.repository.IncidentRepository;
import com.almacen.api.repository.ProductRepository;
import com.almacen.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public IncidentService(
            IncidentRepository incidentRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Crear incidencia
    public Incident create(IncidentDTO dto, String email) {
        Long productId = dto.getProductId();

        User reporter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (productId == null)
            throw new IllegalArgumentException("El id del producto no puede ser null");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Incident incident = new Incident();
        incident.setProduct(product);
        incident.setType(dto.getType());
        incident.setQuantity(dto.getQuantity());
        incident.setDescription(dto.getDescription());
        incident.setStatus("PENDIENTE");
        incident.setReportedBy(reporter);
        incident.setReportedAt(LocalDateTime.now());

        return incidentRepository.save(incident);
    }

    // Listar incidencias
    public List<Incident> findAll() {
        return incidentRepository.findAll();
    }

    // Resolver incidencia
    public Incident resolve(Long id, String email) {

        if (id == null)
            throw new IllegalArgumentException("El id no puede ser null");

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        incident.setStatus("RESUELTO");
        incident.setResolvedBy(manager);
        incident.setResolvedAt(LocalDateTime.now());

        // Ajustar stock
        Product product = incident.getProduct();
        product.setQuantity(product.getQuantity() - incident.getQuantity());

        return incidentRepository.save(incident);
    }

    // Rechazar incidencia
    public Incident reject(Long id, String email) {

        if (id == null)
            throw new IllegalArgumentException("El id no puede ser null");

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        incident.setStatus("RECHAZADO");
        incident.setResolvedBy(manager);
        incident.setResolvedAt(LocalDateTime.now());

        return incidentRepository.save(incident);
    }
}
