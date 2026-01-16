package com.almacen.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigDTO {
    private String companyName;
    private int lowStockThreshold;
    private String currency;
    private boolean autoApproveMovements;
    private boolean requireIncidentApproval;
    private boolean enableNotifications;
    private String defaultLocation;
    private int maxStockPerProduct;
}
