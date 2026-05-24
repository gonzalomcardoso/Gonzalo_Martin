package com.mercadona.test.repository;

import com.mercadona.test.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByStore_Id(Long storeId);
}
