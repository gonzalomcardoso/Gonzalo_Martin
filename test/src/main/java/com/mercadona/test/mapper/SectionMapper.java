package com.mercadona.test.mapper;

import com.mercadona.test.dto.SectionDTO;
import com.mercadona.test.model.Section;

public class SectionMapper {

    public static SectionDTO toDTO(Section section) {
        SectionDTO dto = new SectionDTO();
        dto.setId(section.getId());
        dto.setNombre(section.getNombre());
        dto.setHorasNecesarias(section.getHorasNecesarias());
        return dto;
    }
}

