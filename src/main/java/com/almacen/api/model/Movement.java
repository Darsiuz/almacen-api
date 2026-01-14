package com.almacen.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String type; // ENTRADA / SALIDA

    private int quantity;

    private String reason;

    @ManyToOne
    private User user;

    private String status; // PENDIENTE / APROBADO / RECHAZADO

    private LocalDateTime date = LocalDateTime.now();
}
