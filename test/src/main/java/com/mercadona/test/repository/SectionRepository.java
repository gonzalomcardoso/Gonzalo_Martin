package com.mercadona.test.repository;

import com.mercadona.test.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByStore_Id(Long storeId);
}
