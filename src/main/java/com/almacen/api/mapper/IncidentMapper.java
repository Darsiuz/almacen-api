package com.almacen.api.mapper;

import com.almacen.api.dto.IncidentDTO;
import com.almacen.api.model.Incident;

public class IncidentMapper {

    public static IncidentDTO toDTO(Incident incident) {
        return new IncidentDTO(
                incident.getId(),
                incident.getProduct().getId(),
                incident.getProduct().getName(),
                incident.getType(),
                incident.getQuantity(),
                incident.getDescription(),
                incident.getStatus(),
                incident.getReportedBy().getName(),
                incident.getReportedAt(),
                incident.getResolvedBy() != null ? incident.getResolvedBy().getName() : null,
                incident.getResolvedAt()
        );
    }
}
