package com.almacen.api.service;

import org.springframework.stereotype.Service;

import com.almacen.api.dto.SystemConfigDTO;
import com.almacen.api.mapper.SystemConfigMapper;
import com.almacen.api.model.SystemConfig;
import com.almacen.api.repository.SystemConfigRepository;


@Service
public class SystemConfigService {

    private final SystemConfigRepository repository;
    private final SystemConfigMapper mapper;

    public SystemConfigService(SystemConfigRepository repository, SystemConfigMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public SystemConfig getEntity() {
        return repository.findFirstByOrderByIdAsc()
                .orElseGet(() -> repository.save(
                        new SystemConfig(
                                null,
                                "Almacen",
                                10,
                                "PEN",
                                false,
                                true,
                                false,
                                "Almacen Principal",
                                1000)));
    }

    public SystemConfigDTO getConfig() {
        return mapper.toDTO(getEntity());
    }

    
    public SystemConfigDTO updateConfig(SystemConfigDTO dto) {
        SystemConfig entity = getEntity();
        mapper.updateEntity(entity, dto);
        if (entity == null) {
            throw new RuntimeException("No se pudo obtener la configuración del sistema.");
        }
        repository.save(entity);
        return mapper.toDTO(entity);
    }
}
