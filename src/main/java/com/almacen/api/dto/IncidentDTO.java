package com.almacen.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDTO {
    private Long id;
    private String productName;
    private String type;
    private int quantity;
    private String description;
    private String status;
    private String reportedBy;
    private LocalDateTime reportedAt;
    private String resolvedBy;
    private LocalDateTime resolvedAt;
}
