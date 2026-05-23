package com.mercadona.test.controller;

import com.mercadona.test.model.Worker;
import com.mercadona.test.model.WorkerSectionAssignment;
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
    public ResponseEntity<List<Worker>> getWorkers(@PathVariable Long storeId) {
        return ResponseEntity.ok(workerService.getWorkersByStore(storeId));
    }

    // -----------------------------------
    // CREAR TRABAJADOR
    // -----------------------------------
    @PostMapping
    public ResponseEntity<Worker> createWorker(
            @PathVariable Long storeId,
            @RequestBody Worker worker
    ) {
        return ResponseEntity.ok(workerService.createWorker(worker));
    }

    // -----------------------------------
    // ACTUALIZAR TRABAJADOR
    // -----------------------------------
    @PutMapping("/{workerId}")
    public ResponseEntity<Worker> updateWorker(
            @PathVariable Long workerId,
            @RequestBody Worker worker
    ) {
        return ResponseEntity.ok(workerService.updateWorker(workerId, worker));
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
    public ResponseEntity<WorkerSectionAssignment> assignHours(
            @PathVariable Long workerId,
            @RequestParam Long sectionId,
            @RequestParam Integer horas
    ) {
        return ResponseEntity.ok(workerService.assignHours(workerId, sectionId, horas));
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
