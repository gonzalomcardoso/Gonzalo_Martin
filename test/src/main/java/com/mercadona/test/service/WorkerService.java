package com.mercadona.test.service;

import com.mercadona.test.model.Store;
import com.mercadona.test.model.Section;
import com.mercadona.test.model.Worker;
import com.mercadona.test.model.WorkerSectionAssignment;
import com.mercadona.test.repository.StoreRepository;
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
    // LISTAR TRABAJADORES POR TIENDA (según asignaciones)
    // -----------------------------
    public List<Worker> getWorkersByStore(Long storeId) {
        return assignmentRepository.findAll()
                .stream()
                .filter(a -> a.getSection().getStore().getId().equals(storeId))
                .map(WorkerSectionAssignment::getWorker)
                .distinct()
                .toList();
    }

    // -----------------------------
    // CREAR TRABAJADOR (sin tienda)
    // -----------------------------
    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    // -----------------------------
    // ACTUALIZAR TRABAJADOR
    // -----------------------------
    public Worker updateWorker(Long id, Worker updated) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        worker.setNombre(updated.getNombre());
        worker.setApellidos(updated.getApellidos());
        worker.setDni(updated.getDni());
        worker.setHorasDisponibles(updated.getHorasDisponibles());

        return workerRepository.save(worker);
    }

    // -----------------------------
    // ELIMINAR TRABAJADOR
    // -----------------------------
    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    // -----------------------------
    // ASIGNAR HORAS (POST /assignments)
    // -----------------------------
    public WorkerSectionAssignment assignHours(Long workerId, Long sectionId, Integer hours) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        if (hours <= 0) {
            throw new RuntimeException("Las horas deben ser mayores que cero");
        }

        if (worker.getHorasDisponibles() < hours) {
            throw new RuntimeException("El trabajador no tiene horas suficientes");
        }

        WorkerSectionAssignment assignment = WorkerSectionAssignment.builder()
                .worker(worker)
                .section(section)
                .horasAsignadas(hours)
                .build();

        worker.setHorasDisponibles(worker.getHorasDisponibles() - hours);
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

        worker.setHorasDisponibles(worker.getHorasDisponibles() + assignment.getHorasAsignadas());
        workerRepository.save(worker);

        assignmentRepository.delete(assignment);
    }
}
