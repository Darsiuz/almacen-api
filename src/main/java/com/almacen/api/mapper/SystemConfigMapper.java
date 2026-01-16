package com.almacen.api.mapper;

import com.almacen.api.dto.SystemConfigDTO;
import com.almacen.api.model.SystemConfig;

public class SystemConfigMapper {

    public static SystemConfigDTO toDTO(SystemConfig config) {
        return new SystemConfigDTO(
                config.getCompanyName(),
                config.getLowStockThreshold(),
                config.getCurrency(),
                config.isAutoApproveMovements(),
                config.isRequireIncidentApproval(),
                config.isEnableNotifications(),
                config.getDefaultLocation(),
                config.getMaxStockPerProduct()
        );
    }
}
