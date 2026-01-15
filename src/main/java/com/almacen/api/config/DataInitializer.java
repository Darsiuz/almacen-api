package com.almacen.api.config;

import com.almacen.api.model.Role;
import com.almacen.api.model.SystemConfig;
import com.almacen.api.repository.RoleRepository;
import com.almacen.api.repository.SystemConfigRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            SystemConfigRepository systemConfigRepository
    ) {
        return args -> {

            // Para los roles de usuario
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(null, "ADMIN"));
                roleRepository.save(new Role(null, "MANAGER"));
                roleRepository.save(new Role(null, "OPERATOR"));
                roleRepository.save(new Role(null, "AUDITOR"));
            }

            // Para los SYSTEM CONFIG (1 REGISTRO)
            if (systemConfigRepository.count() == 0) {
                SystemConfig config = new SystemConfig();
                config.setCompanyName("Almacén Central");
                config.setLowStockThreshold(20);
                config.setCurrency("USD");
                config.setAutoApproveMovements(false);
                config.setRequireIncidentApproval(true);
                config.setEnableNotifications(true);
                config.setDefaultLocation("Almacén Principal");
                config.setMaxStockPerProduct(1000);

                systemConfigRepository.save(config);
            }
        };
    }
}
