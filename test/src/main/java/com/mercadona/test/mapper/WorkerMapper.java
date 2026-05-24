package com.mercadona.test.mapper;

import com.mercadona.test.dto.WorkerDTO;
import com.mercadona.test.model.Worker;

public class WorkerMapper {

    public static WorkerDTO toDTO(Worker worker) {
        WorkerDTO dto = new WorkerDTO();
        dto.setId(worker.getId());
        dto.setNombre(worker.getNombre());
        dto.setApellidos(worker.getApellidos());
        dto.setDni(worker.getDni());
        dto.setHorasDisponibles(worker.getHorasDisponibles());
        return dto;
    }
}
