package com.mercadona.test.config;

import com.mercadona.test.model.Section;
import com.mercadona.test.model.Store;
import com.mercadona.test.model.Worker;
import com.mercadona.test.repository.SectionRepository;
import com.mercadona.test.repository.StoreRepository;
import com.mercadona.test.repository.WorkerRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StoreRepository storeRepository;
    private final SectionRepository sectionRepository;
    private final WorkerRepository workerRepository;

    public DataSeeder(StoreRepository storeRepository,
                      SectionRepository sectionRepository,
                      WorkerRepository workerRepository) {
        this.storeRepository = storeRepository;
        this.sectionRepository = sectionRepository;
        this.workerRepository = workerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        InputStream excelFile = new ClassPathResource("data.xlsx").getInputStream();
        Workbook workbook = WorkbookFactory.create(excelFile);

        // -----------------------------
        // HOJA 1: STORES
        // -----------------------------
        Sheet storeSheet = workbook.getSheet("stores");
        for (Row row : storeSheet) {
            if (row.getRowNum() == 0) continue; // saltar cabecera

            String nombre = row.getCell(0).getStringCellValue();

            Store store = Store.builder()
                    .nombre(nombre)
                    .build();

            storeRepository.save(store);
        }

        // -----------------------------
        // HOJA 2: SECTIONS
        // -----------------------------
        Sheet sectionSheet = workbook.getSheet("sections");
        for (Row row : sectionSheet) {
            if (row.getRowNum() == 0) continue;

            String nombre = row.getCell(0).getStringCellValue();
            int horasNecesarias = (int) row.getCell(1).getNumericCellValue();
            String storeNombre = row.getCell(2).getStringCellValue();

            Store store = storeRepository.findAll()
                    .stream()
                    .filter(s -> s.getNombre().equals(storeNombre))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Store no encontrada: " + storeNombre));

            Section section = Section.builder()
                    .nombre(nombre)
                    .horasNecesarias(horasNecesarias)
                    .store(store)
                    .build();

            sectionRepository.save(section);
        }

        // -----------------------------
        // HOJA 3: WORKERS
        // -----------------------------
        Sheet workerSheet = workbook.getSheet("workers");
        for (Row row : workerSheet) {
            if (row.getRowNum() == 0) continue;

            String nombre = row.getCell(0).getStringCellValue();
            String apellidos = row.getCell(1).getStringCellValue();
            String dni = row.getCell(2).getStringCellValue();
            int horasDisponibles = (int) row.getCell(3).getNumericCellValue();

            Worker worker = Worker.builder()
                    .nombre(nombre)
                    .apellidos(apellidos)
                    .dni(dni)
                    .horasDisponibles(horasDisponibles)
                    .build();

            workerRepository.save(worker);
        }

        workbook.close();
        System.out.println(">>> Datos cargados desde Excel correctamente");
    }
}
