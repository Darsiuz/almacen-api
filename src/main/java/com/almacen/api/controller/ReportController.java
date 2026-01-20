package com.almacen.api.controller;

import com.almacen.api.dto.ReportSummaryDTO;
import com.almacen.api.model.Product;
import com.almacen.api.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/inventory")
    public List<Product> inventoryReport() {
        return reportService.inventoryReport();
    }

    @GetMapping("/movement")
    public List<?> movementReport() {
        return reportService.movementReport();
    }

    @GetMapping("/incident")
    public List<?> incidentReport() {
        return reportService.incidentReport();
    }

    @GetMapping("/summary")
    public ReportSummaryDTO summary() {
        return reportService.summaryReport();
    }
}
