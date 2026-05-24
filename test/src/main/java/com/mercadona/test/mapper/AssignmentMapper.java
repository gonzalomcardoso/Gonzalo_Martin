package com.mercadona.test.mapper;

import com.mercadona.test.dto.AssignmentDTO;
import com.mercadona.test.model.WorkerSectionAssignment;

public class AssignmentMapper {

    public static AssignmentDTO toDTO(WorkerSectionAssignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setWorkerId(assignment.getWorker().getId());
        dto.setSectionId(assignment.getSection().getId());
        dto.setHorasAsignadas(assignment.getHorasAsignadas());
        return dto;
    }
}
