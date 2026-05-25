package com.mercadona.test.service;

import com.mercadona.test.dto.*;
import com.mercadona.test.model.Section;
import com.mercadona.test.model.Store;
import com.mercadona.test.model.WorkerSectionAssignment;
import com.mercadona.test.repository.SectionRepository;
import com.mercadona.test.repository.StoreRepository;
import com.mercadona.test.repository.WorkerSectionAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final StoreRepository storeRepository;
    private final SectionRepository sectionRepository;
    private final WorkerSectionAssignmentRepository assignmentRepository;

    public ReportService(StoreRepository storeRepository,
                         SectionRepository sectionRepository,
                         WorkerSectionAssignmentRepository assignmentRepository) {
        this.storeRepository = storeRepository;
        this.sectionRepository = sectionRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public StoreReportDTO getStoreReport(Long storeId) {

        // 1. Verificar tienda
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con id: " + storeId));

        // 2. Secciones de la tienda
        List<Section> sections = sectionRepository.findAll()
                .stream()
                .filter(s -> s.getStore().getId().equals(storeId))
                .toList();

        // 3. Todas las asignaciones
        List<WorkerSectionAssignment> assignments = assignmentRepository.findAll();

        // 4. Agrupar asignaciones por sección
        Map<Long, List<WorkerSectionAssignment>> assignmentsBySection = assignments.stream()
                .filter(a -> a.getSection().getStore().getId().equals(storeId))
                .collect(Collectors.groupingBy(a -> a.getSection().getId()));

        // 5. Construir DTO de secciones
        List<SectionReportDTO> sectionReports = sections.stream().map(section -> {

            List<WorkerAssignmentDTO> workerAssignments = assignmentsBySection
                    .getOrDefault(section.getId(), List.of())
                    .stream()
                    .map(a -> new WorkerAssignmentDTO(
                            a.getWorker().getId(),
                            a.getWorker().getNombre(),
                            a.getWorker().getApellidos(),
                            a.getHorasAsignadas()
                    ))
                    .toList();

            return new SectionReportDTO(
                    section.getId(),
                    section.getNombre(),
                    section.getHorasNecesarias(),
                    workerAssignments
            );

        }).toList();

        // 6. Retornar reporte final
        return new StoreReportDTO(store.getId(), store.getNombre(), sectionReports);
    }

    // ------------------------------------------------------------
    // 2️⃣ Reporte de secciones con horas no cubiertas
    // ------------------------------------------------------------
    public UncoveredSectionsReportDTO getUncoveredSectionsReport(Long storeId) {

        // 1. Verificar tienda
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con id: " + storeId));

        // 2. Secciones de la tienda
        List<Section> sections = sectionRepository.findAll()
                .stream()
                .filter(s -> s.getStore().getId().equals(storeId))
                .toList();

        // 3. Todas las asignaciones
        List<WorkerSectionAssignment> assignments = assignmentRepository.findAll();

        // 4. Agrupar horas asignadas por sección
        Map<Long, Integer> horasAsignadasPorSeccion = assignments.stream()
                .filter(a -> a.getSection().getStore().getId().equals(storeId))
                .collect(Collectors.groupingBy(
                        a -> a.getSection().getId(),
                        Collectors.summingInt(WorkerSectionAssignment::getHorasAsignadas)
                ));

        // 5. Construir lista de secciones con horas faltantes
        List<UncoveredSectionDTO> uncovered = sections.stream()
                .map(section -> {
                    int horasAsignadas = horasAsignadasPorSeccion.getOrDefault(section.getId(), 0);
                    int horasNecesarias = section.getHorasNecesarias();
                    int faltantes = horasNecesarias - horasAsignadas;

                    if (faltantes > 0) {
                        return new UncoveredSectionDTO(
                                section.getId(),
                                section.getNombre(),
                                horasNecesarias,
                                horasAsignadas,
                                faltantes
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        // 6. Retornar reporte final
        return new UncoveredSectionsReportDTO(
                store.getId(),
                store.getNombre(),
                uncovered
        );
    }
}
