package com.almacen.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {
    private Long id;
    private String productName;
    private String type;
    private int quantity;
    private String status;
    private String user;
    private LocalDateTime date;
}
