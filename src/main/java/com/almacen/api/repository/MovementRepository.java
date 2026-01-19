package com.almacen.api.repository;

import com.almacen.api.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByStatus(String status);
    List<Movement> findByType(String type);
    List<Movement> findByProductId(Long productId);
}
