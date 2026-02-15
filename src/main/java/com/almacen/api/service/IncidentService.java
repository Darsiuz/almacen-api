package com.almacen.api.service;

import com.almacen.api.dto.IncidentDTO;
import com.almacen.api.model.Incident;
import com.almacen.api.model.Product;
import com.almacen.api.model.User;
import com.almacen.api.repository.IncidentRepository;
import com.almacen.api.repository.ProductRepository;
import com.almacen.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SystemConfigService systemConfigService;

    public IncidentService(
            IncidentRepository incidentRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            SystemConfigService systemConfigService) {
        this.incidentRepository = incidentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.systemConfigService = systemConfigService;
    }

    private void applyStockAdjustment(Product product, String type, int quantity) {

        if ("ENTRADA".equalsIgnoreCase(type)) {
            product.setQuantity(product.getQuantity() + quantity);
        } else {
            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Stock insuficiente");
            }
            product.setQuantity(product.getQuantity() - quantity);
        }
    }

    // Crear incidencia
    @Transactional
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
        // incident.setStatus("PENDIENTE");
        incident.setReportedBy(reporter);
        incident.setReportedAt(LocalDateTime.now());

        boolean requireApproval = systemConfigService.getEntity().isRequireIncidentApproval();

        if (requireApproval) {
            incident.setStatus("PENDIENTE");
        } else {
            incident.setStatus("RESUELTO");

            // aplicar ajuste de stock automaticamente
            applyStockAdjustment(product, dto.getType(), dto.getQuantity());
        }

        return incidentRepository.save(incident);
    }

    // Listar incidencias
    public List<Incident> findAll() {
        return incidentRepository.findAll();
    }

    // Resolver incidencia
    @Transactional
    public Incident resolve(Long id, String email) {

        if (id == null)
            throw new IllegalArgumentException("El id no puede ser null");

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        // VALIDAR ESTADO
        if (!"PENDIENTE".equalsIgnoreCase(incident.getStatus())) {
            throw new RuntimeException("La incidencia ya fue procesada");
        }

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = incident.getProduct();

        applyStockAdjustment(product, incident.getType(), incident.getQuantity());

        incident.setStatus("RESUELTO");
        incident.setResolvedBy(manager);
        incident.setResolvedAt(LocalDateTime.now());

        return incidentRepository.save(incident);
    }

    // Rechazar incidencia
    @Transactional
    public Incident reject(Long id, String email) {

        if (id == null)
            throw new IllegalArgumentException("El id no puede ser null");

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        if (!"PENDIENTE".equalsIgnoreCase(incident.getStatus())) {
            throw new RuntimeException("La incidencia ya fue procesada");
        }

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        incident.setStatus("RECHAZADO");
        incident.setResolvedBy(manager);
        incident.setResolvedAt(LocalDateTime.now());

        return incidentRepository.save(incident);
    }
}
