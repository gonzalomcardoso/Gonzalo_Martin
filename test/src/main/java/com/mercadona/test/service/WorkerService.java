@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final SectionRepository sectionRepository;
    private final WorkerSectionAssignmentRepository assignmentRepository;
    private final StoreRepository storeRepository;

    public WorkerService(
            WorkerRepository workerRepository,
            SectionRepository sectionRepository,
            WorkerSectionAssignmentRepository assignmentRepository,
            StoreRepository storeRepository
    ) {
        this.workerRepository = workerRepository;
        this.sectionRepository = sectionRepository;
        this.assignmentRepository = assignmentRepository;
        this.storeRepository = storeRepository;
    }

    public List<Worker> getWorkersByStore(Long storeId) {
        return workerRepository.findByStore_Id(storeId);
    }

    public Worker createWorker(Long storeId, WorkerDTO dto) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

        Worker worker = Worker.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .dni(dto.getDni())
                .horasDisponibles(dto.getHorasDisponibles())
                .store(store)
                .build();

        return workerRepository.save(worker);
    }

    public Worker updateWorker(Long workerId, WorkerDTO dto) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        worker.setNombre(dto.getNombre());
        worker.setApellidos(dto.getApellidos());
        worker.setDni(dto.getDni());
        worker.setHorasDisponibles(dto.getHorasDisponibles());

        return workerRepository.save(worker);
    }

    public void deleteWorker(Long workerId) {
        workerRepository.deleteById(workerId);
    }

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

        WorkerSectionAssignment assignment = WorkerSectionAssignment.builder()
                .worker(worker)
                .section(section)
                .horasAsignadas(horas)
                .build();

        worker.setHorasDisponibles(worker.getHorasDisponibles() - horas);
        workerRepository.save(worker);

        return assignmentRepository.save(assignment);
    }

    public void unassignHours(Long workerId, Long sectionId) {

        WorkerSectionAssignment assignment = assignmentRepository
                .findByWorker_Id(workerId)
                .stream()
                .filter(a -> a.getSection().getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        Worker worker = assignment.getWorker();

        worker.setHorasDisponibles(worker.getHorasDisponibles() + assignment.getHorasAsignadas());
        workerRepository.save(worker);

        assignmentRepository.delete(assignment);
    }
}
