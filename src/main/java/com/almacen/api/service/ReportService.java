package com.almacen.api.service;

import com.almacen.api.dto.ReportSummaryDTO;
import com.almacen.api.model.Incident;
import com.almacen.api.model.Movement;
import com.almacen.api.model.Product;
import com.almacen.api.repository.IncidentRepository;
import com.almacen.api.repository.MovementRepository;
import com.almacen.api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ProductRepository productRepository;
    private final MovementRepository movementRepository;
    private final IncidentRepository incidentRepository;

    public ReportService(ProductRepository productRepository,
                         MovementRepository movementRepository,
                         IncidentRepository incidentRepository) {
        this.productRepository = productRepository;
        this.movementRepository = movementRepository;
        this.incidentRepository = incidentRepository;
    }

    // Reporte de inventario
    public List<Product> inventoryReport() {
        return productRepository.findAll();
    }

    // Reporte de movimientos
    public List<Movement> movementReport() {
        return movementRepository.findAll();
    }

    // Reporte de incidencias
    public List<Incident> incidentReport() {
        return incidentRepository.findAll();
    }

    // Resumen de reportes
    public ReportSummaryDTO summaryReport() {
        int totalStock = productRepository.findAll()
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();

        return new ReportSummaryDTO(
                productRepository.count(),
                movementRepository.count(),
                incidentRepository.count(),
                totalStock
        );
    }
}
