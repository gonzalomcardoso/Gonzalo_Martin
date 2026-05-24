package com.mercadona.test.controller;

import com.mercadona.test.dto.WorkerDTO;
import com.mercadona.test.dto.AssignmentDTO;
import com.mercadona.test.mapper.WorkerMapper;
import com.mercadona.test.mapper.AssignmentMapper;

import com.mercadona.test.service.WorkerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    // -----------------------------------
    // LISTAR TRABAJADORES DE UNA TIENDA
    // -----------------------------------
    @GetMapping
    public ResponseEntity<List<WorkerDTO>> getWorkers(@PathVariable Long storeId) {
        return ResponseEntity.ok(
                workerService.getWorkersByStore(storeId)
                        .stream()
                        .map(WorkerMapper::toDTO)
                        .toList()
        );
    }

    // -----------------------------------
    // CREAR TRABAJADOR
    // -----------------------------------
    @PostMapping
    public ResponseEntity<WorkerDTO> createWorker(
            @PathVariable Long storeId,
            @RequestBody WorkerDTO workerDTO
    ) {
        return ResponseEntity.ok(
                WorkerMapper.toDTO(workerService.createWorker(storeId, workerDTO))
        );
    }

    // -----------------------------------
    // ACTUALIZAR TRABAJADOR
    // -----------------------------------
    @PutMapping("/{workerId}")
    public ResponseEntity<WorkerDTO> updateWorker(
            @PathVariable Long workerId,
            @RequestBody WorkerDTO workerDTO
    ) {
        return ResponseEntity.ok(
                WorkerMapper.toDTO(workerService.updateWorker(workerId, workerDTO))
        );
    }

    // -----------------------------------
    // ELIMINAR TRABAJADOR
    // -----------------------------------
    @DeleteMapping("/{workerId}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long workerId) {
        workerService.deleteWorker(workerId);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------
    // ASIGNAR HORAS A UNA SECCIÓN
    // -----------------------------------
    @PostMapping("/{workerId}/assign")
    public ResponseEntity<AssignmentDTO> assignHours(
            @PathVariable Long workerId,
            @RequestParam Long sectionId,
            @RequestParam Integer horas
    ) {
        return ResponseEntity.ok(
                AssignmentMapper.toDTO(workerService.assignHours(workerId, sectionId, horas))
        );
    }

    // -----------------------------------
    // DESASIGNAR HORAS
    // -----------------------------------
    @DeleteMapping("/{workerId}/assign/{sectionId}")
    public ResponseEntity<Void> unassignHours(
            @PathVariable Long workerId,
            @PathVariable Long sectionId
    ) {
        workerService.unassignHours(workerId, sectionId);
        return ResponseEntity.noContent().build();
    }
}
