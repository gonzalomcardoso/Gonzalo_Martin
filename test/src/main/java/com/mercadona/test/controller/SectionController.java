package com.mercadona.test.controller;

import com.mercadona.test.model.Section;
import com.mercadona.test.repository.SectionRepository;
import com.mercadona.test.repository.StoreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/sections")
public class SectionController {

    private final SectionRepository sectionRepository;
    private final StoreRepository storeRepository;

    public SectionController(SectionRepository sectionRepository, StoreRepository storeRepository) {
        this.sectionRepository = sectionRepository;
        this.storeRepository = storeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Section>> getSectionsByStore(@PathVariable Long storeId) {

        if (!storeRepository.existsById(storeId)) {
            return ResponseEntity.notFound().build();
        }

        List<Section> sections = sectionRepository.findAll()
                .stream()
                .filter(s -> s.getStore().getId().equals(storeId))
                .toList();

        return ResponseEntity.ok(sections);
    }
}
