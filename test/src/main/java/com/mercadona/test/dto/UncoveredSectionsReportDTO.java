package com.mercadona.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UncoveredSectionsReportDTO {
    private Long storeId;
    private String storeNombre;
    private List<UncoveredSectionDTO> uncoveredSections;
}
