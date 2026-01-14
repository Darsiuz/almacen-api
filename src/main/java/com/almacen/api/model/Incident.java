package com.almacen.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String type; // DAÑO, PERDIDA, ROBO, VENCIMIENTO, OTRO

    private int quantity;

    private String description;

    private String status; // PENDIENTE, RESUELTO, RECHAZADO

    @ManyToOne
    private User reportedBy;

    @ManyToOne
    private User resolvedBy;

    private LocalDateTime reportedAt = LocalDateTime.now();

    private LocalDateTime resolvedAt;
}
