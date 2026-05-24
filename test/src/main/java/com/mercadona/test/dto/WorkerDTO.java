package com.mercadona.test.dto;

import lombok.Data;

@Data
public class WorkerDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private Integer horasDisponibles;
}
