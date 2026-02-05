package com.almacen.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almacen.api.dto.SystemConfigDTO;
import com.almacen.api.service.SystemConfigService;

@RestController
@RequestMapping("/admin/system")
public class SystemConfigController {

    private final SystemConfigService service;

    public SystemConfigController(SystemConfigService service) {
        this.service = service;
    }

    @GetMapping
    public SystemConfigDTO getConfig() {
        return service.getConfig();
    }

    @PutMapping
    public SystemConfigDTO update(@RequestBody SystemConfigDTO dto) {
        return service.updateConfig(dto);
    }
}
