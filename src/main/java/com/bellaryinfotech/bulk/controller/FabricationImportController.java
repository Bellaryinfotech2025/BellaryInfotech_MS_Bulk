package com.bellaryinfotech.bulk.controller;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bellaryinfotech.bulk.model.OrderFabricationImport;
import com.bellaryinfotech.bulk.repo.OrderFabricationImportRepository;
import com.bellaryinfotech.bulk.service.ExcelImportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fabrication")
public class FabricationImportController {

    private static final Logger log = LoggerFactory.getLogger(FabricationImportController.class);

    @Autowired
    private ExcelImportService excelImportService;
    
    @Autowired
    private OrderFabricationImportRepository repository;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                log.error("Uploaded file is empty");
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Please select a file to upload"
                ));
            }

            // Check if file is an Excel file
            if (!file.getOriginalFilename().endsWith(".xlsx") && !file.getOriginalFilename().endsWith(".xls")) {
                log.error("Uploaded file is not an Excel file: {}", file.getOriginalFilename());
                return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Please upload an Excel file (.xlsx or .xls)"
                ));
            }

            log.info("Processing file: {}, size: {} bytes", file.getOriginalFilename(), file.getSize());
        
            // Process the file
            int recordsImported = excelImportService.importExcelToDatabase(file);

            log.info("Import completed. Records imported: {}", recordsImported);
        
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "File imported successfully");
            response.put("recordsImported", recordsImported);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to import data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "status", "error",
                    "message", "Failed to import data: " + e.getMessage(),
                    "details", e.toString()
                ));
        }
    }
    
    @GetMapping("/imported-data")
    public ResponseEntity<?> getImportedData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ifaceId") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            
            Page<OrderFabricationImport> dataPage = repository.findAll(pageable);
            
            Map<String, Object> response = new HashMap<>();
            response.put("data", dataPage.getContent());
            response.put("currentPage", dataPage.getNumber());
            response.put("totalItems", dataPage.getTotalElements());
            response.put("totalPages", dataPage.getTotalPages());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to retrieve data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve data: " + e.getMessage()
                    ));
        }
    }
    
    @GetMapping("/template")
    public ResponseEntity<?> getTemplateInfo() {
        try {
            // This endpoint helps users understand what columns are expected
            Map<String, Object> templateInfo = new HashMap<>();
            
            // First sheet columns
            Map<String, String> sheet1Columns = new HashMap<>();
            sheet1Columns.put("ORDER_NUMBER", "Order number (e.g., ORD_321)");
            sheet1Columns.put("ORIG_LINE_NO", "Original line number (e.g., 1)");
            sheet1Columns.put("LINE_NO", "Line number (e.g., 1.1)");
            sheet1Columns.put("DRAWING NO", "Drawing number (e.g., MEC-11-12-Q7NX-DE-41-17362)");
            sheet1Columns.put("DESCRIPTION", "Description of the drawing");
            
            // Second sheet columns
            Map<String, String> sheet2Columns = new HashMap<>();
            sheet2Columns.put("ERECTION MKD.", "Erection marked number (e.g., A-11)");
            sheet2Columns.put("ITEM NO.", "Item number (e.g., 1)");
            sheet2Columns.put("SECTION", "Section information (e.g., PL.300x32)");
            sheet2Columns.put("LENGTH", "Length value (numeric)");
            sheet2Columns.put("qty", "Quantity (numeric)");
            sheet2Columns.put("UNIT", "Unit (numeric)");
            sheet2Columns.put("TOTAL WT.", "Total weight (numeric)");
            sheet2Columns.put("QTY. REQD", "Quantity required (numeric)");
            sheet2Columns.put("EREC. MKD. WT.", "Erection marked weight (numeric)");
            sheet2Columns.put("REMARKS", "Remarks or notes (e.g., Completed)");
            
            templateInfo.put("sheet1", sheet1Columns);
            templateInfo.put("sheet2", sheet2Columns);
            templateInfo.put("instructions", "The Excel file should have two sheets with the columns listed above. " +
                    "The system will match records between sheets based on order number and line number.");
            
            return ResponseEntity.ok(templateInfo);
        } catch (Exception e) {
            log.error("Failed to retrieve template info", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "Failed to retrieve template info: " + e.getMessage()
                    ));
        }
    }
}


