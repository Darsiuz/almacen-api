package com.almacen.api.mapper;

import org.springframework.stereotype.Component;

import com.almacen.api.dto.SystemConfigDTO;
import com.almacen.api.model.SystemConfig;

@Component
public class SystemConfigMapper {

    public SystemConfigDTO toDTO(SystemConfig entity) {
        SystemConfigDTO dto = new SystemConfigDTO();
        dto.setCompanyName(entity.getCompanyName());
        dto.setLowStockThreshold(entity.getLowStockThreshold());
        dto.setCurrency(entity.getCurrency());
        dto.setAutoApproveMovements(entity.isAutoApproveMovements());
        dto.setRequireIncidentApproval(entity.isRequireIncidentApproval());
        dto.setEnableNotifications(entity.isEnableNotifications());
        dto.setDefaultLocation(entity.getDefaultLocation());
        dto.setMaxStockPerProduct(entity.getMaxStockPerProduct());
        return dto;
    }

    public void updateEntity(SystemConfig entity, SystemConfigDTO dto) {
        entity.setCompanyName(dto.getCompanyName());
        entity.setLowStockThreshold(dto.getLowStockThreshold());
        entity.setCurrency(dto.getCurrency());
        entity.setAutoApproveMovements(dto.isAutoApproveMovements());
        entity.setRequireIncidentApproval(dto.isRequireIncidentApproval());
        entity.setEnableNotifications(dto.isEnableNotifications());
        entity.setDefaultLocation(dto.getDefaultLocation());
        entity.setMaxStockPerProduct(dto.getMaxStockPerProduct());
    }
    
}
