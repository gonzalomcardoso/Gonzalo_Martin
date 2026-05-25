package com.mercadona.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UncoveredSectionDTO {
    private Long sectionId;
    private String nombre;
    private Integer horasNecesarias;
    private Integer horasAsignadas;
    private Integer horasFaltantes;
}
