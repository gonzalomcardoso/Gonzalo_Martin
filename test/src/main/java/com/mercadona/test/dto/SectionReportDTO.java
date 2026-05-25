package com.mercadona.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SectionReportDTO {
    private Long sectionId;
    private String nombre;
    private Integer horasNecesarias;
    private List<WorkerAssignmentDTO> assignments;
}
