package com.almacen.api.config;

import com.almacen.api.model.Role;
import com.almacen.api.model.SystemConfig;
import com.almacen.api.model.User;
import com.almacen.api.repository.RoleRepository;
import com.almacen.api.repository.SystemConfigRepository;
import com.almacen.api.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            SystemConfigRepository systemConfigRepository,
            PasswordEncoder passwordEncoder) {
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

            // Para los usuarios
            if (userRepository.count() == 0) {

                Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
                Role managerRole = roleRepository.findByName("MANAGER").orElseThrow();
                Role operatorRole = roleRepository.findByName("OPERATOR").orElseThrow();
                Role auditorRole = roleRepository.findByName("AUDITOR").orElseThrow();

                User admin = new User();
                admin.setName("Admin Principal");
                admin.setEmail("admin@almacen.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(adminRole);
                admin.setActive(true);
                userRepository.save(admin);

                User manager = new User();
                manager.setName("Manager López");
                manager.setEmail("manager@almacen.com");
                manager.setPassword(passwordEncoder.encode("manager123"));
                manager.setRole(managerRole);
                manager.setActive(true);
                userRepository.save(manager);

                User operator = new User();
                operator.setName("Operador García");
                operator.setEmail("operator@almacen.com");
                operator.setPassword(passwordEncoder.encode("operator123"));
                operator.setRole(operatorRole);
                operator.setActive(true);
                userRepository.save(operator);

                User auditor = new User();
                auditor.setName("Auditor Martínez");
                auditor.setEmail("auditor@almacen.com");
                auditor.setPassword(passwordEncoder.encode("auditor123"));
                auditor.setRole(auditorRole);
                auditor.setActive(true);
                userRepository.save(auditor);

            }
        };
    }
}
