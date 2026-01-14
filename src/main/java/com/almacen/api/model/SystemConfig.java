package com.almacen.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "system_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private int lowStockThreshold;

    private String currency;

    private boolean autoApproveMovements;

    private boolean requireIncidentApproval;

    private boolean enableNotifications;

    private String defaultLocation;

    private int maxStockPerProduct;
}
