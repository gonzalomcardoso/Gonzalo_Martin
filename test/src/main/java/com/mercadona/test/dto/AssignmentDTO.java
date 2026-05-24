package com.mercadona.test.dto;

import lombok.Data;

@Data
public class AssignmentDTO {

    private Long id;
    private Long workerId;
    private Long sectionId;
    private Integer horasAsignadas;
}
