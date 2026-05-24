package com.mercadona.test.repository;

import com.mercadona.test.model.WorkerSectionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkerSectionAssignmentRepository extends JpaRepository<WorkerSectionAssignment, Long> {

    List<WorkerSectionAssignment> findByWorker_Id(Long workerId);

    List<WorkerSectionAssignment> findBySection_Id(Long sectionId);

    List<WorkerSectionAssignment> findBySection_Store_Id(Long storeId);
}
