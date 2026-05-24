package com.mercadona.test.controller;

import com.mercadona.test.dto.StoreDTO;
import com.mercadona.test.dto.SectionDTO;
import com.mercadona.test.dto.WorkerDTO;

import com.mercadona.test.mapper.StoreMapper;
import com.mercadona.test.mapper.SectionMapper;
import com.mercadona.test.mapper.WorkerMapper;

import com.mercadona.test.repository.StoreRepository;
import com.mercadona.test.repository.SectionRepository;
import com.mercadona.test.repository.WorkerRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final SectionRepository sectionRepository;
    private final WorkerRepository workerRepository;

    public StoreController(StoreRepository storeRepository,
                           SectionRepository sectionRepository,
                           WorkerRepository workerRepository) {
        this.storeRepository = storeRepository;
        this.sectionRepository = sectionRepository;
        this.workerRepository = workerRepository;
    }

    @GetMapping
    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreMapper::toDTO)
                .toList();
    }

    @GetMapping("/{storeId}/sections")
    public List<SectionDTO> getSectionsByStore(@PathVariable Long storeId) {

        storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        return sectionRepository.findByStore_Id(storeId)
                .stream()
                .map(SectionMapper::toDTO)
                .toList();
    }

    @GetMapping("/{storeId}/workers")
    public List<WorkerDTO> getWorkersByStore(@PathVariable Long storeId) {

        storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        return workerRepository.findByStore_Id(storeId)
                .stream()
                .map(WorkerMapper::toDTO)
                .toList();
    }
}
