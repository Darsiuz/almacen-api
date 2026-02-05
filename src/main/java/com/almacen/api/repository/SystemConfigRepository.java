package com.almacen.api.repository;

import com.almacen.api.model.SystemConfig;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
     Optional<SystemConfig> findFirstByOrderByIdAsc();
}
