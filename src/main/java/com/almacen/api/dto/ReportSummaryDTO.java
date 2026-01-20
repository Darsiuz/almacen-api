package com.almacen.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDTO {
    private long totalProducts;
    private long totalMovements;
    private long totalIncidents;
    private int totalStock;
}
