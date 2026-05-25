package com.mercadona.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerAssignmentDTO {
    private Long workerId;
    private String workerNombre;
    private String workerApellidos;
    private Integer horasAsignadas;
}
