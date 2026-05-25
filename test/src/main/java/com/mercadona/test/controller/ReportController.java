package com.mercadona.test.controller;

import com.mercadona.test.dto.StoreReportDTO;
import com.mercadona.test.dto.UncoveredSectionsReportDTO;
import com.mercadona.test.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores/{storeId}/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    
    @GetMapping
    public StoreReportDTO getStoreReport(@PathVariable Long storeId) {
        return reportService.getStoreReport(storeId);
    }

    
    @GetMapping("/uncovered")
    public UncoveredSectionsReportDTO getUncoveredSectionsReport(@PathVariable Long storeId) {
        return reportService.getUncoveredSectionsReport(storeId);
    }
}
