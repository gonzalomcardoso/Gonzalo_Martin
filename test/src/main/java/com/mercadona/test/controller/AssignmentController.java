package com.mercadona.test.controller;

import com.mercadona.test.model.WorkerSectionAssignment;
import com.mercadona.test.service.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final WorkerService workerService;

    public AssignmentController(WorkerService workerService) {
        this.workerService = workerService;
    }

    // -----------------------------------
    // CREAR ASIGNACIÓN
    // -----------------------------------
    @PostMapping
    public ResponseEntity<WorkerSectionAssignment> assignHours(
            @RequestParam Long workerId,
            @RequestParam Long sectionId,
            @RequestParam Integer hours
    ) {
        return ResponseEntity.ok(workerService.assignHours(workerId, sectionId, hours));
    }

    // -----------------------------------
    // ELIMINAR ASIGNACIÓN
    // -----------------------------------
    @DeleteMapping
    public ResponseEntity<Void> unassignHours(
            @RequestParam Long workerId,
            @RequestParam Long sectionId
    ) {
        workerService.unassignHours(workerId, sectionId);
        return ResponseEntity.noContent().build();
    }
}
