package com.mercadona.test.controller;

import com.mercadona.test.dto.AssignmentDTO;
import com.mercadona.test.mapper.AssignmentMapper;
import com.mercadona.test.model.WorkerSectionAssignment;
import com.mercadona.test.repository.WorkerSectionAssignmentRepository;
import com.mercadona.test.service.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final WorkerService workerService;
    private final WorkerSectionAssignmentRepository assignmentRepository;

    public AssignmentController(WorkerService workerService,
                                WorkerSectionAssignmentRepository assignmentRepository) {
        this.workerService = workerService;
        this.assignmentRepository = assignmentRepository;
    }

    // -----------------------------------
    // LISTAR TODAS LAS ASIGNACIONES
    // -----------------------------------
    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        List<AssignmentDTO> assignments = assignmentRepository.findAll()
                .stream()
                .map(AssignmentMapper::toDTO)
                .toList();
        return ResponseEntity.ok(assignments);
    }

    // -----------------------------------
    // ASIGNAR HORAS A UNA SECCIÓN
    // -----------------------------------
    @PostMapping("/assign")
    public ResponseEntity<AssignmentDTO> assignHours(
            @RequestParam Long workerId,
            @RequestParam Long sectionId,
            @RequestParam Integer horas
    ) {
        WorkerSectionAssignment assignment = workerService.assignHours(workerId, sectionId, horas);
        return ResponseEntity.ok(AssignmentMapper.toDTO(assignment));
    }

    // -----------------------------------
    // DESASIGNAR HORAS
    // -----------------------------------
    @DeleteMapping("/unassign")
    public ResponseEntity<Void> unassignHours(
            @RequestParam Long workerId,
            @RequestParam Long sectionId
    ) {
        workerService.unassignHours(workerId, sectionId);
        return ResponseEntity.noContent().build();
    }
}
