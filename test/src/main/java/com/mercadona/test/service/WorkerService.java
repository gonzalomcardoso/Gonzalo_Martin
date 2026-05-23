package com.mercadona.test.service;

import com.mercadona.test.model.Section;
import com.mercadona.test.model.Worker;
import com.mercadona.test.model.WorkerSectionAssignment;
import com.mercadona.test.repository.SectionRepository;
import com.mercadona.test.repository.WorkerRepository;
import com.mercadona.test.repository.WorkerSectionAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final SectionRepository sectionRepository;
    private final WorkerSectionAssignmentRepository assignmentRepository;

    public WorkerService(
            WorkerRepository workerRepository,
            SectionRepository sectionRepository,
            WorkerSectionAssignmentRepository assignmentRepository
    ) {
        this.workerRepository = workerRepository;
        this.sectionRepository = sectionRepository;
        this.assignmentRepository = assignmentRepository;
    }

    // -----------------------------
    // CRUD BÁSICO
    // -----------------------------

    public List<Worker> getWorkersByStore(Long storeId) {
        return workerRepository.findAll(); // Se puede filtrar por tienda si lo necesitas
    }

    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public Worker updateWorker(Long id, Worker updated) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        worker.setNombre(updated.getNombre());
        worker.setApellidos(updated.getApellidos());
        worker.setDni(updated.getDni());
        worker.setHorasDisponibles(updated.getHorasDisponibles());

        return workerRepository.save(worker);
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    // -----------------------------
    // ASIGNACIÓN DE HORAS
    // -----------------------------

    public WorkerSectionAssignment assignHours(Long workerId, Long sectionId, Integer horas) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        if (horas <= 0) {
            throw new RuntimeException("Las horas deben ser mayores que cero");
        }

        if (worker.getHorasDisponibles() < horas) {
            throw new RuntimeException("El trabajador no tiene horas suficientes");
        }

        // Crear asignación
        WorkerSectionAssignment assignment = WorkerSectionAssignment.builder()
                .worker(worker)
                .section(section)
                .horasAsignadas(horas)
                .build();

        // Restar horas disponibles
        worker.setHorasDisponibles(worker.getHorasDisponibles() - horas);
        workerRepository.save(worker);

        return assignmentRepository.save(assignment);
    }

    // -----------------------------
    // DESASIGNAR HORAS
    // -----------------------------

    public void unassignHours(Long workerId, Long sectionId) {

        WorkerSectionAssignment assignment = assignmentRepository
                .findAll()
                .stream()
                .filter(a -> a.getWorker().getId().equals(workerId)
                        && a.getSection().getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        Worker worker = assignment.getWorker();

        // Devolver horas al trabajador
        worker.setHorasDisponibles(worker.getHorasDisponibles() + assignment.getHorasAsignadas());
        workerRepository.save(worker);

        assignmentRepository.delete(assignment);
    }
}
