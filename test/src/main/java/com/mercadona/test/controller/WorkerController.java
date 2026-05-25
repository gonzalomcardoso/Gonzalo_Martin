package com.mercadona.test.controller;

import com.mercadona.test.model.Worker;
import com.mercadona.test.model.WorkerSectionAssignment;
import com.mercadona.test.service.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    // -----------------------------------
    // LISTAR TRABAJADORES DE UNA TIENDA
    // -----------------------------------
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Worker>> getWorkersByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(workerService.getWorkersByStore(storeId));
    }

    // -----------------------------------
    // CREAR TRABAJADOR (sin tienda)
    // -----------------------------------
    @PostMapping
    public ResponseEntity<Worker> createWorker(@RequestBody Worker worker) {
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
}
